package com.rocky.learn_java.algorithm;

//you can also use imports, for example:
import java.util.*;
//you can write to stdout for debugging purposes, e.g.
//System.out.println("this is a debug message");

class Solution {
	public int solution(String S) {
		// write your code in Java SE 8
		Map<String, List<PhoneCall>> callMap = constructCallMap(S);
		removeLongestCall(callMap);
		int totalFee = callMap.values().stream().map(calls -> calculateTotalFee(calls)).reduce(0, Integer::sum);
		return totalFee;

	}

	private int calculateTotalFee(List<PhoneCall> calls) {
		return calls.stream().map(PhoneCall::getFees).reduce(0, Integer::sum);
	}

	private Map<String, List<PhoneCall>> constructCallMap(String log) {
		String[] calls = log.split("\n");
		Map<String, List<PhoneCall>> callMap = new HashMap<>();
		for (int i = 0; i < calls.length; i++) {
			PhoneCall call = new PhoneCall(calls[i]);
			if (callMap.get(call.callNumber) == null) {
				List<PhoneCall> callList = new ArrayList<>();
				callList.add(call);
				callMap.put(call.callNumber, callList);
			} else {

				callMap.get(call.callNumber).add(call);
			}
		}
		return callMap;
	}

	private void removeLongestCall(Map<String, List<PhoneCall>> callMap) {
		int longestDuration = 0;
		for (List<PhoneCall> calls : callMap.values()) {
			int totalDuration = 0;
			for (int i = 0; i < calls.size(); i++) {
				totalDuration += calls.get(i).totalDuration;
			}
			longestDuration = Math.max(totalDuration, longestDuration);
		}
		List<Integer> longestCallNumbers = new ArrayList<>();
		for (String callNumber : callMap.keySet()) {
			int totalDuration = 0;
			List<PhoneCall> calls = callMap.get(callNumber);
			for (int i = 0; i < calls.size(); i++) {
				totalDuration += calls.get(i).totalDuration;
			}
			if (totalDuration == longestDuration) {
				longestCallNumbers.add(Integer.valueOf(callNumber.replaceAll("-", "")));
			}
		}
		Collections.sort(longestCallNumbers);
		Integer freeCall = longestCallNumbers.get(0);
		String freeCallStr = null;
		for (String callNumber : callMap.keySet()) {
			if (freeCall.equals(Integer.valueOf(callNumber.replaceAll("-", "")))) {
				freeCallStr = callNumber;
			}
		}
		callMap.remove(freeCallStr);
	}

	class PhoneCall {
		int durationInSeconds;
		int durationInMinutes;
		int durationInHours;
		int totalDuration;
		String callNumber;
		int fees;

		PhoneCall(String line) {
			String[] records = line.split(",");
			callNumber = records[1];

			String[] durations = records[0].split(":");
			durationInHours = Integer.valueOf(durations[0]);
			durationInMinutes = Integer.valueOf(durations[1]);
			durationInSeconds = Integer.valueOf(durations[2]);
			totalDuration = durationInSeconds + durationInMinutes * 60 + durationInHours * 3600;
			fees = getFees();
		}

		int getFees() {
			if (totalDuration < 5 * 60) {
				return totalDuration * 3;
			} else {
				if (durationInSeconds > 0) {
					return (totalDuration / 60 + 1) * 150;
				}
				return (totalDuration / 60) * 150;
			}
		}
	}

	public static void main(String[] args) {
		String S = "00:01:07,400-234-090\n" + "00:05:01,701-080-080\n" + "00:05:00,400-234-090";
		Solution s = new Solution();
		System.out.println(s.solution(S));
	}
}
