def subscribeTag = ["isNightFlight"]
def subscribeData = ["money"]

def group = {isNightFlight, money ->
	return 1
}

def groupTarget = "NightFlightNum"