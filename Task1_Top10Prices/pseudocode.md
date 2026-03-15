# Task 1: Top 10 Highest Stock Prices — Pseudocode

![Flow Diagram](/Task1_Top10Prices/flowdiagram.png)
---

## 1. Mapper
```
for each line in file:
if line is header or empty:
skip
ticker = extract ticker from filename
high_price = value at column 2
emit (ticker, high_price)
```

- Mapper reads each line independently.  
- Extracts High price and the ticker name.  
- Emits thousands of `(ticker, high_price)` pairs per file.

---

## 2. Shuffle

- Hadoop groups all outputs by key (Ticker) across all mappers.  
- Sends them to the reducer.

---

## 3. Reducer
```
for each ticker key:
max = maximum of all values
emit (ticker, global_max)
```

- The reducer finds the **all-time peak price** for each stock.  
- Writes output to HDFS.

---

## Top10.java (Post-processing Step)

**Input:** Reducer output file(s)  
**Output:** Top 10 stocks by peak price
```
read all (ticker, max) pairs from HDFS output
sort by max descending
print top 10
```

- This is done locally after Hadoop job finishes.  
- Produces the final **Top 10 table**.

---

