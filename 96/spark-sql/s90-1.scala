Problem Scenario 90 : You have been given below two files 

course.txt 

id,course 
1,Hadoop 
2, Spark 
3,HBase 

Fee.txt 
id,fee 
2, 3900 
3,4200 
4,2900 

Accomplish the following activities. 
1. Select all the courses and their fees , whether tee is listed or not. 
2. Select all the available fees and respective course. If course does not exists still list the fee 
3. Select all the courses and their fees , whether tee is listed or not. However, ignore records having tee as null. 

Solution : 

Step 1 . 
hdfs dfs -mkdir sparksq14 
hdfs dfs -put course.txt sparksq14/ 
hdfs dfs -put tee.txt sparksq14/ 

Step 2 : Now in spark shell 

//load the data into a new ROD 
val course = sc.textFile("sparksq14/course.txt") 
val fee = sc.textFile("sparksq14/fee.txt") 
//Return the first element in this RDD 

course.first() 
fee.first() 

//define the schema using a case class 
case class Course(id: Integer, name: String) 
case class Fee(id: Integer, fee: Integer) 

//create an RDD ot Product objects 
val courseRDD = course.map(_.split(",")).map(c => 
val feeRDD = fee.map(Fee(c(0).tolnt,c(1).tolnt)) 

courseRDD.first() 
courseRDD.count() 
feeRDD.first() 
feeRDD.count() 

// change RDD of Product objects to a DataFrame 
val courseDF = courseRDD.toDF() 
val feeDF = feeRDD.toDF() 

// register the DataFrame as a temp table 
courseDF.registerTempTable("course") 
feeDF.registerTempTable("fee") 

// Select data from table 
val results = sqlContext.sql( "SELECT * FROM course") 
results.show() 
val results = sqlContext.sql( "SELECT * FROM fee") 
results.show() 

val results = sqlContext.sql("SELECT * FROM course LEFT JOIN fee ON course.id = fee.id") 
results.show() 
val results = sqlContext.sql("SELECT * FROM course RIGHT JOIN fee ON course.id = fee.id") 
results.show() 

val results = sqlContext.sql("SELECT * FROM course LEFT JOIN fee ON course.id = fee.id where fee.id IS NULL") 
results.show() 
