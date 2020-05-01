select 
BUSYO_ID,SYAIN_ID,SYAIN_NAME
from SK_SYAIN
where 1=1
and BUSYO_ID = '0001'
and SYAIN_ID = ''
and SYAIN_NAME like '%%'
order by SYAIN_ID