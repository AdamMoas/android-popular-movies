//package com.portfolio.moas.adam.popularmovies;
//
//import com.google.gson.Gson;
//
///**
// * Created by adam on 17/01/2018.
// */
//
//public final class JsonUtils {
//
//    private static final String temporaryMockAPIResponse = "[{\"vote_count\":5835,\"id\":346364,\"video\":false,\"vote_average\":7.1,\"title\":\"It\",\"popularity\":723.926632,\"poster_path\":\"\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg\",\"original_language\":\"en\",\"original_title\":\"It\",\"genre_ids\":[18,27,53],\"backdrop_path\":\"\\/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg\",\"adult\":false,\"overview\":\"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\"release_date\":\"2017-09-05\"},{\"vote_count\":1495,\"id\":353486,\"video\":false,\"vote_average\":6.4,\"title\":\"Jumanji: Welcome to the Jungle\",\"popularity\":681.838101,\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: Welcome to the Jungle\",\"genre_ids\":[28,12,35,10751],\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\",\"adult\":false,\"overview\":\"The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":5301,\"id\":211672,\"video\":false,\"vote_average\":6.4,\"title\":\"Minions\",\"popularity\":237.560284,\"poster_path\":\"\\/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\"original_language\":\"en\",\"original_title\":\"Minions\",\"genre_ids\":[10751,16,12,35],\"backdrop_path\":\"\\/qLmdjn2fv0FV2Mh4NBzMArdA0Uu.jpg\",\"adult\":false,\"overview\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\"release_date\":\"2015-06-17\"},{\"vote_count\":3170,\"id\":181808,\"video\":false,\"vote_average\":7.2,\"title\":\"Star Wars: The Last Jedi\",\"popularity\":511.361559,\"poster_path\":\"\\/xGWVjewoXnJhvxKW619cMzppJDQ.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/5Iw7zQTHVRBOYpA0V6z0yypOPZh.jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.\",\"release_date\":\"2017-12-13\"}]";
//    private Gson gson;
//    private Movie[] movies;
//
//    private JsonUtils(String response, Gson gson) {
//        this.gson = gson;
//    }
//
//    public static Movie[] getMovieJSONResponse(String apiResponse, Gson gson) {
//        return movies = gson.fromJson(temporaryMockAPIResponse, Movie[].class);
////        System.out.println("111 First id: " + movie1[0].title);
////        System.out.println("111 Second id: " + movie1[1].title);
////        System.out.println("111 First poster path: " + movie1[0].poster_path);
////
////        System.out.println("Test==" + gson.toJson(movie1));
//
////        String posterPath = movie1[0].poster_path;
//    }
//
//    public static String getPosterPath(int position) {
//        return getMovieJSONResponse(temporaryMockAPIResponse, gson)[position].poster_path;
//    }
//
//}
