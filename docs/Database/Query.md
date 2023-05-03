# Query


### 날짜 중간에 이가 빠지지 않도록 날짜로만 이루어진 가상 쿼리 만들기
```sql
select str_to_date('20220101', '%Y%m%d') + INTERVAL nl.n  DAY AS Date
FROM (
    SELECT @row := @row + 1 AS n
    FROM (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) t1,
         (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) t2,
         (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) t3,
         (SELECT @row := -1) r
) nl
WHERE n BETWEEN 0 AND (DATEDIFF(str_to_date('20220101', '%Y%m%d'), str_to_date('20220102', '%Y%m%d')) * -1);
```


