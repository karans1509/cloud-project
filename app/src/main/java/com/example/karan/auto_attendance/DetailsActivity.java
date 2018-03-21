package com.example.karan.auto_attendance;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    public String weekNum;
    DynamoDBMapper mapper;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        weekNum = b.getString("number");
        Log.d("Week Number", weekNum);
//        weekNum = (TextView)findViewById(R.id.detailWeek);
//        weekNum.setText(intent.getStringExtra("number"));


        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.mapper = DynamoDBMapper.builder().dynamoDBClient(dynamoDBClient)
                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration()).build();

        getAll();
    }

    public void readData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                AttendanceDO newsItem = mapper.load(
                        AttendanceDO.class,
                        "100290458");

                // Item read
                 Log.d("News Item:", newsItem.getName());
            }
        }).start();
    }

    public void getAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AttendanceDO attendanceDOdo = new AttendanceDO();
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                final List<AttendanceDO> result = mapper.scan(AttendanceDO.class, scanExpression);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                        studentList = new ArrayList<Student>();

                        switch (weekNum) {
                            case "WEEK 1": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekOne());
                                studentList.add(student);
                            }
                                break;
                            case "WEEK 2": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekTwo());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 3": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekThree());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 4": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekFour());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 5": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekFive());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 6": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekSix());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 7": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekSeven());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 8": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekEight());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 9": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekNine());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 10": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekTen());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 11": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekEleven());
                                studentList.add(student);
                            }
                                break;

                            case "WEEK 12": for(int i = 0; i < result.size(); i++) {
                                Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekTwelve());
                                studentList.add(student);
                            }
                                break;

                            default:
                        }

//                        for(int i = 0; i < result.size(); i++) {
//                            Student student = new Student(result.get(i).getName(), result.get(i).getUserId(), result.get(i).getWeekOne());
//                            studentList.add(student);
//                        }
                        adapter = new StudentAdapter(DetailsActivity.this, studentList);
                        recyclerView.setAdapter(adapter);
                    }
                });

                Log.d("result", result.toString());
            }
        }).start();
    }

    public void addItem(View view) {
//        final AttendanceDO attendanceDO = new AttendanceDO();
//        attendanceDO.setUserId("100294155");
//        attendanceDO.setName("Angad");
//        attendanceDO.setWeekOne("Y");
//        attendanceDO.setWeekTwo("Y");
//        attendanceDO.setWeekThree("Y");
//        attendanceDO.setWeekFour("N");
//        attendanceDO.setWeekFive("N");
//        attendanceDO.setWeekSix("N");
//        attendanceDO.setWeekSeven("N");
//        attendanceDO.setWeekEight("N");
//        attendanceDO.setWeekNine("Y");
//        attendanceDO.setWeekTen("Y");
//        attendanceDO.setWeekEleven("Y");
//        attendanceDO.setWeekTwelve("Y");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mapper.save(attendanceDO);
//            }
//        }).start();
    }
}
