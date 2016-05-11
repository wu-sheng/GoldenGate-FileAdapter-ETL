import com.ai.edc.etl.transform.group.TagJudge

subscribeTag = []
subscribeData = ["numSum"]
subscribeOldData = true

def groupNum = {numSum, old_numSum ->
	return Long.parseLong(numSum) - Long.parseLong(old_numSum)
}

def group = {numSum, old_num ->
	groupResult = new String[1];
	groupResult[0] = "departSum=" + groupNum(numSum, old_num)
	return groupResult
}