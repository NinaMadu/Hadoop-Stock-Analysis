# Task 2: Yearly Highest Gain/Loss — Pseudocode

![Figure 1: Flow diagram of Task 2 — Yearly Highest Gain/Loss MapReduce process](/Task2_YearlyGainLoss/flowdiagram.png)
---

## Mapper Pseudocode
```
BEGIN MAPPER

FOR each input line from stock file
IF line is header THEN
skip the line
ENDIF

Split line using comma (,)
date  <- column[0]
open  <- column[1]
close <- column[4]

year <- extract year from date

gain <- close - open

filename <- current input file name
ticker <- remove ".txt" from filename

EMIT (year, ticker, gain, date)

END FOR

END MAPPER
```

**Explanation**

- Reads each stock file line by line.  
- Skips the header line.  
- Calculates **daily gain/loss** (`Close - Open`).  
- Emits key-value pairs: `(year, ticker, gain, date)`.

---

## Reducer Pseudocode
```
BEGIN REDUCER

FOR each year key

maxGain <- (-∞)
maxLoss <- (+∞)

FOR each (ticker, gain, date) in values

    IF gain > maxGain THEN
        maxGain <- gain
        maxGainTicker <- ticker
        maxGainDate <- date
    ENDIF

    IF gain < maxLoss THEN
        maxLoss <- gain
        maxLossTicker <- ticker
        maxLossDate <- date
    ENDIF

END FOR

OUTPUT (year,
        highest gain ticker + gain + date,
        highest loss ticker + loss + date)

END FOR

END REDUCER
```

**Explanation**

- For each year, finds the **largest daily gain** and **largest daily loss** among all tickers.  
- Outputs per year:  
  - Ticker with highest gain and its gain/date.  
  - Ticker with highest loss and its loss/date.  
- Results can be used to analyze **yearly stock volatility** and trends.

---

