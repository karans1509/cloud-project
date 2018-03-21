    const AWS = require("aws-sdk");
    const s3 = new AWS.S3({
        params: { Bucket: 'bucket' }
    });

    const sns = new AWS.SNS();
    const endpointArn = "endpoint";

    const rekognition = new AWS.Rekognition();

    const dynamo = new AWS.DynamoDB({
        region: 'us-west-2',
        apiVersion: '2012-08-10',
    });

    const iotdata = new AWS.IotData({endpoint: 'endpoint'});

    exports.handler = (event, context, callback) => {

        var eventText = JSON.stringify(event);
        console.log("Received event:", eventText);

        var bucketParams = {
            Bucket: 'code-breakers',
        };

        s3.listObjects(bucketParams, function (err, data) {
            if (err) {
                console.log(err)
            }
            else {
                console.log("List of photos")
                let keys = [];
                for (var i = 0; i < data.Contents.length; i++) {
                    console.log(data.Contents[i].Key.substring(0, 10))
                    if(data.Contents[i].Key.substring(0, 10) == "students/1") {
                        keys.push(data.Contents[i].Key)
                        console.log("Key: " + keys[i]);
                    }
                }
                console.log("keys added");
                let match = false

                for (let i = 0; i < keys.length; i++) {
                    console.log("Loop: "+keys[i])
                    let params = {
                        SimilarityThreshold: 70,
                        SourceImage: {
                            S3Object: {
                                Bucket: "code-breakers",
                                Name: event.imageKey
                            }
                        },
                        TargetImage: {
                            S3Object: {
                                Bucket: "code-breakers",
                                Name: keys[i]
                            }
                        }
                    };

                    if (match == false) {
                        rekognition.compareFaces(params, (err, data) => {
                            if (err) {
                                console.log(err)
                            }
                            else {
                                if (data.FaceMatches.length == 1) {
                                    console.log("Match found");
                                    match = true
                                    console.log(keys[i])

                                    let modify = keys[i].substring(9, 18)
                                    console.log(modify)
                                    let params = {
                                        TableName: 'classattendance-mobilehub-1377230471-attendance',
                                        Key: {
                                            "userId": { "S": modify },
                                        },
                                        UpdateExpression: 'set #week =:w',
                                        ExpressionAttributeNames: { '#week': "week_eleven" },
                                        ExpressionAttributeValues: {
                                            ":w": { "S": 'Y' }
                                        }
                                    };

                                    dynamo.updateItem(params, function (err, data) {
                                        if (err) {
                                            console.error("Error occured", err);
                                        }
                                        console.log("Data updated")
                                        
                                        let params2 = {
                                            TableName: 'classattendance-mobilehub-1377230471-attendance',
                                            Key: {
                                                "userId": { "S": modify },
                                            }
                                        }
                                        
                                        dynamo.getItem(params2, function(err, data2){
                                            if(err) {
                                                console.error(err)
                                                return
                                            }
                                            let username = data2.Item.name.S
                                            
                                            let iotparams = {
                                                topic: 'topic_2',
                                                payload: username,
                                                qos: 0
                                            };


                                            iotdata.publish(iotparams, function(err, data){
                                                if(err){
                                                    console.log(err);
                                                }
                                                else{
                                                    console.log("success?");
                                                    //context.succeed(event);
                                                }
                                            });
                                            
                                            
                                            let payload = {
                                                default: 'Hello World',
                                                GCM: {
                                                    notification: {
                                                        body: `${username} has joined the class`,
                                                        title: "Count Me In",
                                                        sound: "enabled"
                                                    }
                                                }   
                                            }   
                                            payload.GCM = JSON.stringify(payload.GCM);
                                            payload = JSON.stringify(payload)
                                            
                                            console.log('sending push');
                                            
                                            sns.publish({
                                                Message: payload,
                                                MessageStructure: 'json',
                                                TargetArn: endpointArn
                                            }, function(err, data) {
                                                if (err) {
                                                  console.log(err.stack);
                                                  return;
                                              }

                                              console.log('push sent');
                                              console.log(data);
                                          });
                                            
                                            
                                        })
                                        
                                        
                                    });
                                }
                                else {
                                    // console.log("not matched")
                                }
                            }
                        })
    }
    }
    }
    })

    callback(null, event.imageKey);
    };