subscribeTag = ["isNightFlight"]
subscribeData = ["money"]

def group = {isNightFlight, money ->
	return 1
}

groupTarget = "NightFlightNum"