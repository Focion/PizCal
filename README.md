# Cal

Cal是一个日历控件，RecyclerView实现

#### 效果图
![cal](https://raw.githubusercontent.com/Focion/PizRes/master/images/img_cal_view.png)

#### 版本

  - 1.0

#### 使用介绍

###### xml
```xml
      <cn.focion.cal.CalView
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          app:cal_yearBold="true"
          app:cal_yearTextColor="@color/colorPrimary" />
```

###### Activity
```java
      CalView calView = (CalView) findViewById(R.id.calview);
      calView.setOnCalSelectListener(new CalView.OnCalSelectListener() {
          @Override
          public void onCalSelect(String date) {
              // To do something 获取选择的时间 格式 yyyy-MM-dd
          }
      });
      // 获取数据模型
      CalModel calModel = calView.getCalModel();
      // 创建数据 当前爱你显示2017-01至2017-12的日历
      calModel.setYear(2017, 2017);
      // 创建数据 当前显示2017-05至2018-06的日历
      calModel.setYear(2017, 2018, 5, 6);
      // 去除日历 当前2017-05不能选择
      calModel.setSplitMonth(2017, 5);
      // 去除日历 当前2017-05, 12、13、14日不能选择
      calModel.setSplitDay(2017, 5, 12, 13, 14);
      // 数据创建
      calModel.build();
      calView.notifyDataSetChanged();
```

#### attr
```xml
      // 年份的背景，字体大小，字体颜色，加粗
      <attr name="cal_yearBg" format="reference" />
      <attr name="cal_yearTextSize" format="dimension" />
      <attr name="cal_yearTextColor" format="color" />
      <attr name="cal_yearBold" format="boolean" />
      // 日-六的背景，字体大小，字体颜色，加粗
      <attr name="cal_weekBg" format="reference" />
      <attr name="cal_weekTextSize" format="dimension" />
      <attr name="cal_weekTextColor" format="color" />
      <attr name="cal_weekBold" format="boolean" />
      // 日的背景，字体大小，颜色
      <attr name="cal_dayBg" format="reference" />
      <attr name="cal_dayTextSize" format="dimension" />
      <attr name="cal_dayTextColor" format="color" />
```

#### License
  * [Apache Licene 2.0]


[Apache Licene 2.0]:<http://www.apache.org/licenses/LICENSE-2.0>
