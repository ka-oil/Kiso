select 
BUSYO_ID,SYAIN_ID,SYAIN_NAME
from SK_SYAIN
where 1=1
and BUSYO_ID = 'D01'
and SYAIN_ID = '0001'
and SYAIN_NAME like '%‘¾˜Y%'
order by SYAIN_ID