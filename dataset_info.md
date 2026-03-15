# Dataset Description

Dataset Link : https://www.kaggle.com/datasets/borismarjanovic/price-volume-data-for-all-us-stocks-etfs?resource=download

The dataset used in this project is the **Huge Stock Market Dataset** obtained from Kaggle. It contains historical daily trading data for thousands of publicly traded companies in the United States stock market. The dataset provides long-term price information that can be used for financial analysis, trend detection, and stock market research.

The dataset is organized as **individual 7165 `.txt` files**, where each file represents the historical trading records of a single stock ticker. This structure makes the dataset well suited for **Hadoop MapReduce processing**, since each file can be processed independently and in parallel across distributed nodes.

Each record in a file contains the following fields:

| Field   | Description                                                |
| ------- | ---------------------------------------------------------- |
| Date    | Trading date                                               |
| Open    | Opening price of the stock                                 |
| High    | Highest price during the trading day                       |
| Low     | Lowest price during the trading day                        |
| Close   | Closing price of the stock                                 |
| Volume  | Number of shares traded during the day                     |
| OpenInt | Open Interest (number of outstanding derivative contracts) |

### Example Record

```
Date,Open,High,Low,Close,Volume,OpenInt
1999-11-18,30.713,33.754,27.002,29.702,66277506,0
```

The dataset spans **multiple decades of stock trading data**, covering thousands of companies and millions of daily price records. Because of its **large size and multi-file structure**, it is suitable for big data analysis using distributed computing frameworks such as Hadoop.

In this project, the dataset is used to perform two main analyses using Hadoop MapReduce:

1. **Finding the Top 10 Highest Stock Prices** recorded in the dataset.
2. **Identifying the Highest Daily Gain and Highest Daily Loss for Each Year** across all stocks.

These analyses demonstrate how Hadoop can efficiently process large-scale financial datasets and extract meaningful insights from distributed data sources.
