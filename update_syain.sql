insert into SK_SYAIN
				+ "(SYAIN_ID,SYAIN_NAME,SYAIN_AGE,SYAIN_SEIBETU,SYAIN_ZYUSYO,BUSYO_ID) \n" + "values  \n" + "('" + syainId
				+ "','" + syainName + "','" + syainAge + "','" + syainSeibetu + "','" + syainZyusyo + "','" + syainSyozoku
				+ "')

update SK_SYAIN
set SYAIN_ID = 'busyoId', SYAIN_NAME = 'busyoName', SYAIN_AGE = 'syainAge',
SYAIN_SEIBETU = 'syainSeibetu', SYAIN_ZYUSYO = 'syainZyusyo',
BUSYO_ID = 'syainSyozoku'
where SYAIN_ID = 'syainId'