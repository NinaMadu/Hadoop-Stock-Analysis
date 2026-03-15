# Hadoop Stock Market Analysis

## Project Overview

This project performs **large-scale stock market analysis using Hadoop MapReduce**.
The dataset contains historical daily stock price data where each company ticker is stored in a separate `.txt` file.

Each file contains the following fields:

Date, Open, High, Low, Close, Volume, OpenInt

Two MapReduce tasks are implemented:

1. **Task 1 – Top 10 Highest Prices**
2. **Task 2 – Yearly Highest Gain and Loss**

Environment used:

* Hadoop 3.4.3
* Java 21
* Ubuntu (WSL)

---

# 1. Prepare Dataset

The dataset consists of 7195 of `.txt` files where each file represents one stock ticker.
Dataset Link : https://www.kaggle.com/datasets/borismarjanovic/price-volume-data-for-all-us-stocks-etfs?resource=download

use "/stocks" dataset from the above source. 

Example structure of a stock file:

```
Date,Open,High,Low,Close,Volume,OpenInt
1999-11-18,30.713,33.754,27.002,29.702,66277506,0
1999-11-19,28.986,29.027,26.872,27.257,16142920,0
```

Copy dataset from Windows into WSL:

```
mkdir ~/stocks
cp /mnt/d/.../Stocks/*.txt ~/stocks
```

Verify files:

```
ls ~/stocks | head
```

---

# 2. Upload Dataset to Hadoop (HDFS)

Create input directory in HDFS:

```
hdfs dfs -mkdir /stock_input
```

Upload all stock files:

```
hdfs dfs -put ~/stocks/*.txt /stock_input
```

Verify upload:

```
hdfs dfs -ls /stock_input | head
```

---

# 3. TASK 1 – Top 10 Highest Prices

This task finds the **top 10 highest stock prices in the dataset**.

## Source Files

```
PeakPriceDriver.java
PeakPriceMapper.java
PeakPriceReducer.java
Top10.java
```

### Step 1 – Navigate to Task Folder

```
cd Task1_Top10Prices/src
```

### Step 2 – Compile Java Files

```
javac -classpath $(hadoop classpath) -d . *.java
```

### Step 3 – Create JAR File

```
jar -cvf PeakPrice.jar *.class
```

### Step 4 – Run Hadoop Job

```
hadoop jar PeakPrice.jar PeakPriceDriver /stock_input /top10_output
```

### Step 5 – View Results

```
hdfs dfs -cat /top10_output/part-r-00000
```

The output lists the **top 10 highest stock prices recorded**.

---

# 4. TASK 2 – Yearly Highest Gain and Loss

This task calculates **daily gain/loss** and determines:

* Highest gain stock for each year
* Highest loss stock for each year

Gain formula:

```
Gain = Close Price - Open Price
```

## Source Files

```
StockYearlyGainLoss.java
StockYearlyMapper.java
StockYearlyReducer.java
```

---

## Step 1 – Navigate to Task Folder

```
cd Task2_YearlyGainLoss/src
```

---

## Step 2 – Compile Java Files

```
javac -classpath $(hadoop classpath) -d . *.java
```

---

## Step 3 – Create JAR File

```
jar -cvf StockYearlyGainLoss.jar *.class
```

---

## Step 4 – Run Hadoop Job

```
hadoop jar StockYearlyGainLoss.jar StockYearlyGainLoss /stock_input /stock_yearly_output
```

---

## Step 5 – View Results

```
hdfs dfs -cat /stock_yearly_output/part-r-00000
```

Example output:

```
2008  Highest Gain: XYZ 12.4 on 2008-05-12 | Highest Loss: ABC -10.3 on 2008-09-18
```

This shows the **largest daily stock movement for each year**.

---

---

# 6. Screenshots and Results

Screenshot are added in folders as below

- /installation_evidence - Evidence for hadoop installation and example execution
- /Task1_Top10Prices/results - Screenshots of execution logs and results
- /Task2_YearlyGainLoss/results - Screenshots of execution logs and results

---
