select ss.SYAIN_ID, ss.SYAIN_NAME, sl.SYAIN_ROLL
from SK_LOGIN sl, SK_SYAIN ss
where 1=1
and ss.SYAIN_ID = '0001'
and sl.LOGIN_PASS = '0001pw'
--and sl.SYAIN_ID = ss.SYAIN_ID
