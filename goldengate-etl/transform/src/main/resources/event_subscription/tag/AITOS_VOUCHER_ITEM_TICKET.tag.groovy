def columneName = ['flight_date_exp_from', 'flight_date_exp_to', 'flight_time_exp_from', 'flight_time_exp_to']
def tagTable = "AITOS_VOUCHER_ITEM_TICKET_JOIN"

def businessCheck = {data ->
	//检查是否异常夜间飞行
	return true;
}

def tagTarget = "isNightFlight"