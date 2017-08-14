**RWB Sales Statistics** 

- gather daily sales data  
- parse and save to database
- read from database and do aggregations
- statistics

**for this example**  

- daily data will be provided in json format at the end of each day
- format of daily report:

```
{
  "date": "YYYY-MON-DD",
  "totalSales": Int,
  "sales": [
    {
      "saleId": String,
      "saleTime": "YYYY-MON-DD HH24:MI:SS",
      "items": [
        {
          "productName": String,
          "quantity": Double,
          "saleUnits": String,
          "unitPrice": Double 
        },
        ...
      ]
    },
    ...
  ]
    
}
```
