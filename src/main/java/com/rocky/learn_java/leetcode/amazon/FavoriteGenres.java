package com.rocky.learn_java.leetcode.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/discuss/interview-question/373006 Given a map Map<String, List<String>> userSongs with user names as keys and a list of all
 * the songs that the user has listened to as values.
 *
 * Also given a map Map<String, List<String>> songGenres, with song genre as keys and a list of all the songs within that genre as values. The song
 * can only belong to only one genre.
 *
 * The task is to return a map Map<String, List<String>>, where the key is a user name and the value is a list of the user's favorite genre(s).
 * Favorite genre is the most listened to genre. A user can have more than one favorite genre if he/she has listened to the same number of songs per
 * each of the genres.
 *
 * Example 1:
 *
 * Input: userSongs = { "David": ["song1", "song2", "song3", "song4", "song8"], "Emma":  ["song5", "song6", "song7"] }, songGenres = { "Rock":
 * ["song1", "song3"], "Dubstep": ["song7"], "Techno":  ["song2", "song4"], "Pop":     ["song5", "song6"], "Jazz":    ["song8", "song9"] }
 *
 * Output: { "David": ["Rock", "Techno"], "Emma":  ["Pop"] }
 *
 * Explanation: David has 2 Rock, 2 Techno and 1 Jazz song. So he has 2 favorite genres. Emma has 2 Pop and 1 Dubstep song. Pop is Emma's favorite
 * genre. Example 2:
 *
 * Input: userSongs = { "David": ["song1", "song2"], "Emma":  ["song3", "song4"] }, songGenres = {}
 *
 * Output: { "David": [], "Emma":  [] }
 */
public class FavoriteGenres {

    public Map<String, List<String>> favoriteGenre(Map<String, List<String>> userMap, Map<String, List<String>> genreMap) {
        // name, genres
        Map<String, List<String>> result = new HashMap<>();
        Map<String, String> songToGenreMap = this.buildSongToGenreMap(genreMap);
        userMap.keySet().forEach(user -> result.put(user, this.favoriteGenre(userMap.get(user), songToGenreMap)));


//        for (String user : userMap.keySet()) {
//            result.put(user, this.favoriteGenre(userMap.get(user), songToGenreMap));
//        }

        return result;
    }

    private List<String> favoriteGenre(List<String> songs, Map<String, String> songToGenreMap) {
        List<String> genres = new ArrayList<>();
        Map<String, Integer> genreCountMap = new HashMap<>();
        songs.forEach(song -> {
            String genre = songToGenreMap.get(song);
            genreCountMap.put(genre, genreCountMap.getOrDefault(genre, 0) + 1);
        });

        PriorityQueue<Map.Entry<String, Integer>> genreQueue = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        for(Map.Entry<String, Integer> genreCount: genreCountMap.entrySet()) {
            genreQueue.offer(genreCount);
        }

        int maxCount = -1;

        while(genreQueue.isEmpty() == false) {
            Map.Entry<String, Integer> genreCount = genreQueue.poll();
            if(maxCount == -1) {
                maxCount = genreCount.getValue();
                genres.add(genreCount.getKey());
                continue;
            }
            if(genreCount.getValue() == maxCount) {
                genres.add(genreCount.getKey());
            } else {
                break;
            }
        }
        return genres;
    }

    private Map<String, String> buildSongToGenreMap(Map<String, List<String>> genreMap) {
        Map<String, String> songToGenreMap = new HashMap<>();
        for (String genre : genreMap.keySet()) {
            genreMap.get(genre).forEach(song -> songToGenreMap.put(song, genre));
        }
        return songToGenreMap;
    }

    public static void main(String[] args) {
        Map<String, List<String>> userMap = new HashMap<>();
        List<String> list1 = Arrays.asList("song1", "song2", "song3", "song4", "song8");
        List<String> list2 = Arrays.asList("song5", "song6", "song7");
        userMap.put("David", list1);
        userMap.put("Emma", list2);

        Map<String, List<String>> genreMap = new HashMap<>();
        List<String> list3 = Arrays.asList("song1", "song3");
        List<String> list4 = Arrays.asList("song7");
        List<String> list5 = Arrays.asList("song2", "song4");
        List<String> list6 = Arrays.asList("song5", "song6");
        List<String> list7 = Arrays.asList("song8", "song9");
        genreMap.put("Rock", list3);
        genreMap.put("Dubstep", list4);
        genreMap.put("Techno", list5);
        genreMap.put("Pop", list6);
        genreMap.put("Jazz", list7);

        /*Map<String, List<String>> userMap = new HashMap<>();
		List<String> list1 = Arrays.asList("song1", "song2");
		List<String> list2 = Arrays.asList("song3", "song4");
		userMap.put("David", list1);
		userMap.put("Emma", list2);

		Map<String, List<String>> genreMap = new HashMap<>();*/

        System.out.println(new FavoriteGenres().favoriteGenre(userMap, genreMap));
    }
}
