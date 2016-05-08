import com.ai.edc.common.utils.*;

subscribeTag = ["isNightFlight"]
subscribeData = ["money"]

def group = {isNightFlight, money ->
	if(StringUtil.isBlank(isNightFlight)){
		return 1L
	}else{
		return 0L
	}
}

groupTarget = "NightFlightNum"