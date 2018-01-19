package com.portfolio.moas.adam.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {

    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private final int NUMBER_OF_COLUMNS = 2;
    private int movieCount;
    private static final String temporaryMockAPIResponse = "[{\"vote_count\":5835,\"id\":346364,\"video\":false,\"vote_average\":7.1,\"title\":\"It\",\"popularity\":723.926632,\"poster_path\":\"\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg\",\"original_language\":\"en\",\"original_title\":\"It\",\"genre_ids\":[18,27,53],\"backdrop_path\":\"\\/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg\",\"adult\":false,\"overview\":\"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\"release_date\":\"2017-09-05\"},{\"vote_count\":1495,\"id\":353486,\"video\":false,\"vote_average\":6.4,\"title\":\"Jumanji: Welcome to the Jungle\",\"popularity\":681.838101,\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: Welcome to the Jungle\",\"genre_ids\":[28,12,35,10751],\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\",\"adult\":false,\"overview\":\"The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":5301,\"id\":211672,\"video\":false,\"vote_average\":6.4,\"title\":\"Minions\",\"popularity\":237.560284,\"poster_path\":\"\\/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\"original_language\":\"en\",\"original_title\":\"Minions\",\"genre_ids\":[10751,16,12,35],\"backdrop_path\":\"\\/qLmdjn2fv0FV2Mh4NBzMArdA0Uu.jpg\",\"adult\":false,\"overview\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\"release_date\":\"2015-06-17\"},{\"vote_count\":3170,\"id\":181808,\"video\":false,\"vote_average\":7.2,\"title\":\"Star Wars: The Last Jedi\",\"popularity\":511.361559,\"poster_path\":\"\\/xGWVjewoXnJhvxKW619cMzppJDQ.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/5Iw7zQTHVRBOYpA0V6z0yypOPZh.jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.\",\"release_date\":\"2017-12-13\"}]";
    private static final String temporaryMockAPIResponse2 = "{\"page\":1,\"total_results\":19788,\"total_pages\":990,\"results\":[{\"vote_count\":5835,\"id\":346364,\"video\":false,\"vote_average\":7.1,\"title\":\"It\",\"popularity\":723.926632,\"poster_path\":\"\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg\",\"original_language\":\"en\",\"original_title\":\"It\",\"genre_ids\":[18,27,53],\"backdrop_path\":\"\\/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg\",\"adult\":false,\"overview\":\"In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.\",\"release_date\":\"2017-09-05\"},{\"vote_count\":1495,\"id\":353486,\"video\":false,\"vote_average\":6.4,\"title\":\"Jumanji: Welcome to the Jungle\",\"popularity\":681.838101,\"poster_path\":\"\\/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: Welcome to the Jungle\",\"genre_ids\":[28,12,35,10751],\"backdrop_path\":\"\\/rz3TAyd5kmiJmozp3GUbYeB5Kep.jpg\",\"adult\":false,\"overview\":\"The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":3170,\"id\":181808,\"video\":false,\"vote_average\":7.2,\"title\":\"Star Wars: The Last Jedi\",\"popularity\":511.361559,\"poster_path\":\"\\/xGWVjewoXnJhvxKW619cMzppJDQ.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Last Jedi\",\"genre_ids\":[28,12,14,878],\"backdrop_path\":\"\\/5Iw7zQTHVRBOYpA0V6z0yypOPZh.jpg\",\"adult\":false,\"overview\":\"Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.\",\"release_date\":\"2017-12-13\"},{\"vote_count\":314,\"id\":460793,\"video\":false,\"vote_average\":5.9,\"title\":\"Olaf's Frozen Adventure\",\"popularity\":417.70829,\"poster_path\":\"\\/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg\",\"original_language\":\"en\",\"original_title\":\"Olaf's Frozen Adventure\",\"genre_ids\":[12,16,35,10751,14,10402],\"backdrop_path\":\"\\/woCxv9kua5uqtRNaiWUBDzrgZnH.jpg\",\"adult\":false,\"overview\":\"Olaf is on a mission to harness the best holiday traditions for Anna, Elsa, and Kristoff.\",\"release_date\":\"2017-10-27\"},{\"vote_count\":516,\"id\":316029,\"video\":false,\"vote_average\":8,\"title\":\"The Greatest Showman\",\"popularity\":337.795922,\"poster_path\":\"\\/dfhztJRiElqmYW4kpvjYe1gENsD.jpg\",\"original_language\":\"en\",\"original_title\":\"The Greatest Showman\",\"genre_ids\":[18,10751,10402,10749],\"backdrop_path\":\"\\/zpq404Sk7qQ7N4x3xOeNgp74GtU.jpg\",\"adult\":false,\"overview\":\"The story of American showman P.T. Barnum, founder of the circus that became the famous traveling Ringling Bros. and Barnum & Bailey Circus.\",\"release_date\":\"2017-12-20\"},{\"vote_count\":330,\"id\":399055,\"video\":false,\"vote_average\":7.2,\"title\":\"The Shape of Water\",\"popularity\":322.292368,\"poster_path\":\"\\/iLYLADGA5oKGM92Ns1j9CDgk3iI.jpg\",\"original_language\":\"en\",\"original_title\":\"The Shape of Water\",\"genre_ids\":[12,18,14,27,10749,53],\"backdrop_path\":\"\\/bAISaVhsaoyyQMZ55cpTwCdzek4.jpg\",\"adult\":false,\"overview\":\"An other-worldly story, set against the backdrop of Cold War era America circa 1962, where a mute janitor working at a lab falls in love with an amphibious man being held captive there and devises a plan to help him escape.\",\"release_date\":\"2017-12-01\"},{\"vote_count\":1301,\"id\":354912,\"video\":false,\"vote_average\":7.6,\"title\":\"Coco\",\"popularity\":309.46314,\"poster_path\":\"\\/eKi8dIrr8voobbaGzDpe8w0PVbC.jpg\",\"original_language\":\"en\",\"original_title\":\"Coco\",\"genre_ids\":[12,16,10751,14],\"backdrop_path\":\"\\/askg3SMvhqEl4OL52YuvdtY40Yb.jpg\",\"adult\":false,\"overview\":\"Despite his family’s baffling generations-old ban on music, Miguel dreams of becoming an accomplished musician like his idol, Ernesto de la Cruz. Desperate to prove his talent, Miguel finds himself in the stunning and colorful Land of the Dead following a mysterious chain of events. Along the way, he meets charming trickster Hector, and together, they set off on an extraordinary journey to unlock the real story behind Miguel's family history.\",\"release_date\":\"2017-10-27\"},{\"vote_count\":91,\"id\":317091,\"video\":false,\"vote_average\":4.6,\"title\":\"November Criminals\",\"popularity\":266.200179,\"poster_path\":\"\\/m2JxGgarPvtemudLZ1CB6gLJeUb.jpg\",\"original_language\":\"en\",\"original_title\":\"November Criminals\",\"genre_ids\":[80,18,9648],\"backdrop_path\":\"\\/8j089OjNSlmsDqt9vrAAvj021Em.jpg\",\"adult\":false,\"overview\":\"Addison Schacht investigates the murder of his friend Kevin, with the help of Pheobe, and they discover the truth is darker than they ever imagined.\",\"release_date\":\"2017-12-08\"},{\"vote_count\":2636,\"id\":335984,\"video\":false,\"vote_average\":7.4,\"title\":\"Blade Runner 2049\",\"popularity\":254.762608,\"poster_path\":\"\\/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg\",\"original_language\":\"en\",\"original_title\":\"Blade Runner 2049\",\"genre_ids\":[9648,878,53],\"backdrop_path\":\"\\/m7ynwXIvSnhxQPR6pOICrC0L2sO.jpg\",\"adult\":false,\"overview\":\"Thirty years after the events of the first film, a new blade runner, LAPD Officer K, unearths a long-buried secret that has the potential to plunge what's left of society into chaos. K's discovery leads him on a quest to find Rick Deckard, a former LAPD blade runner who has been missing for 30 years.\",\"release_date\":\"2017-10-04\"},{\"vote_count\":197,\"id\":347882,\"video\":false,\"vote_average\":5.4,\"title\":\"Sleight\",\"popularity\":249.349845,\"poster_path\":\"\\/wridRvGxDqGldhzAIh3IcZhHT5F.jpg\",\"original_language\":\"en\",\"original_title\":\"Sleight\",\"genre_ids\":[18,53,28,878],\"backdrop_path\":\"\\/2SEgJ0mHJ7TSdVDbkGU061tR33K.jpg\",\"adult\":false,\"overview\":\"A young street magician is left to take care of his little sister after his mother's passing and turns to drug dealing in the Los Angeles party scene to keep a roof over their heads. When he gets into trouble with his supplier, his sister is kidnapped and he is forced to rely on both his sleight of hand and brilliant mind to save her.\",\"release_date\":\"2017-04-28\"},{\"vote_count\":4312,\"id\":374720,\"video\":false,\"vote_average\":7.4,\"title\":\"Dunkirk\",\"popularity\":242.085637,\"poster_path\":\"\\/ebSnODDg9lbsMIaWg2uAbjn7TO5.jpg\",\"original_language\":\"en\",\"original_title\":\"Dunkirk\",\"genre_ids\":[28,18,36,53,10752],\"backdrop_path\":\"\\/4yjJNAgXBmzxpS6sogj4ftwd270.jpg\",\"adult\":false,\"overview\":\"The story of the miraculous evacuation of Allied soldiers from Belgium, Britain, Canada and France, who were cut off and surrounded by the German army from the beaches and harbour of Dunkirk between May 26th and June 4th 1940 during World War II.\",\"release_date\":\"2017-07-19\"},{\"vote_count\":6410,\"id\":245891,\"video\":false,\"vote_average\":7,\"title\":\"John Wick\",\"popularity\":239.438615,\"poster_path\":\"\\/5vHssUeVe25bMrof1HyaPyWgaP.jpg\",\"original_language\":\"en\",\"original_title\":\"John Wick\",\"genre_ids\":[28,53],\"backdrop_path\":\"\\/umC04Cozevu8nn3JTDJ1pc7PVTn.jpg\",\"adult\":false,\"overview\":\"Ex-hitman John Wick comes out of retirement to track down the gangsters that took everything from him.\",\"release_date\":\"2014-10-22\"},{\"vote_count\":5301,\"id\":211672,\"video\":false,\"vote_average\":6.4,\"title\":\"Minions\",\"popularity\":237.560284,\"poster_path\":\"\\/q0R4crx2SehcEEQEkYObktdeFy.jpg\",\"original_language\":\"en\",\"original_title\":\"Minions\",\"genre_ids\":[10751,16,12,35],\"backdrop_path\":\"\\/qLmdjn2fv0FV2Mh4NBzMArdA0Uu.jpg\",\"adult\":false,\"overview\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\",\"release_date\":\"2015-06-17\"},{\"vote_count\":200,\"id\":271404,\"video\":false,\"vote_average\":4.9,\"title\":\"Beyond Skyline\",\"popularity\":235.759733,\"poster_path\":\"\\/lQUJZIm2nQj4mIsm0sUcznhMdpD.jpg\",\"original_language\":\"en\",\"original_title\":\"Beyond Skyline\",\"genre_ids\":[28,878],\"backdrop_path\":\"\\/m5jvdwlI24XpFDSX79o97z76jis.jpg\",\"adult\":false,\"overview\":\"A tough-as-nails detective embarks on a relentless pursuit to free his son from a nightmarish alien warship.\",\"release_date\":\"2017-10-20\"},{\"vote_count\":136,\"id\":406563,\"video\":false,\"vote_average\":6.2,\"title\":\"Insidious: The Last Key\",\"popularity\":206.531995,\"poster_path\":\"\\/nb9fc9INMg8kQ8L7sE7XTNsZnUX.jpg\",\"original_language\":\"en\",\"original_title\":\"Insidious: The Last Key\",\"genre_ids\":[9648,53,27],\"backdrop_path\":\"\\/auSRjQyVLw212IjATAbO3KOyxZg.jpg\",\"adult\":false,\"overview\":\"Parapsychologist Elise Rainier and her team travel to Five Keys, N.M., to investigate a man's claim of a haunting. Terror soon strikes when Rainier realizes that the house he lives in was her family's old home.\",\"release_date\":\"2018-01-03\"},{\"vote_count\":260,\"id\":353616,\"video\":false,\"vote_average\":6.4,\"title\":\"Pitch Perfect 3\",\"popularity\":202.314183,\"poster_path\":\"\\/hQriQIpHUeh66I89gypFXtqEuVq.jpg\",\"original_language\":\"en\",\"original_title\":\"Pitch Perfect 3\",\"genre_ids\":[35],\"backdrop_path\":\"\\/1qIzlhLGPSm6TxlvXBWe0Q5er7O.jpg\",\"adult\":false,\"overview\":\"After the highs of winning the world championships, the Bellas find themselves split apart and discovering there aren't job prospects for making music with your mouth. But when they get the chance to reunite for an overseas USO tour, this group of awesome nerds will come together to make some music, and some questionable decisions, one last time.\",\"release_date\":\"2017-12-21\"},{\"vote_count\":3349,\"id\":8844,\"video\":false,\"vote_average\":6.9,\"title\":\"Jumanji\",\"popularity\":199.736506,\"poster_path\":\"\\/8wBKXZNod4frLZjAKSDuAcQ2dEU.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji\",\"genre_ids\":[12,14,10751],\"backdrop_path\":\"\\/7k4zEgUZbzMHawDaMc9yIkmY1qR.jpg\",\"adult\":false,\"overview\":\"When siblings Judy and Peter discover an enchanted board game that opens the door to a magical world, they unwittingly invite Alan -- an adult who's been trapped inside the game for 26 years -- into their living room. Alan's only hope for freedom is to finish the game, which proves risky as all three find themselves running from giant rhinoceroses, evil monkeys and other terrifying creatures.\",\"release_date\":\"1995-12-15\"},{\"vote_count\":6540,\"id\":321612,\"video\":false,\"vote_average\":6.8,\"title\":\"Beauty and the Beast\",\"popularity\":191.664307,\"poster_path\":\"\\/tWqifoYuwLETmmasnGHO7xBjEtt.jpg\",\"original_language\":\"en\",\"original_title\":\"Beauty and the Beast\",\"genre_ids\":[10751,14,10749],\"backdrop_path\":\"\\/6aUWe0GSl69wMTSWWexsorMIvwU.jpg\",\"adult\":false,\"overview\":\"A live-action adaptation of Disney's version of the classic tale of a cursed prince and a beautiful young woman who helps him break the spell.\",\"release_date\":\"2017-03-16\"},{\"vote_count\":270,\"id\":371638,\"video\":false,\"vote_average\":7.3,\"title\":\"The Disaster Artist\",\"popularity\":184.640083,\"poster_path\":\"\\/uCH6FOFsDW6pfvbbmIIswuvuNtM.jpg\",\"original_language\":\"en\",\"original_title\":\"The Disaster Artist\",\"genre_ids\":[18,35,36],\"backdrop_path\":\"\\/bAI7aPHQcvSZXvt7L11kMJdS0Gm.jpg\",\"adult\":false,\"overview\":\"An aspiring actor in Hollywood meets an enigmatic stranger by the name of Tommy Wiseau, the meeting leads the actor down a path nobody could have predicted; creating the worst movie ever made.\",\"release_date\":\"2017-11-30\"},{\"vote_count\":90,\"id\":446354,\"video\":false,\"vote_average\":6.6,\"title\":\"The Post\",\"popularity\":169.843682,\"poster_path\":\"\\/qyRwj5VvuTRdJ76o2grP93grNxt.jpg\",\"original_language\":\"en\",\"original_title\":\"The Post\",\"genre_ids\":[18,36],\"backdrop_path\":\"\\/8sb4aBST28vN3rBz704XJczS0Ld.jpg\",\"adult\":false,\"overview\":\"A cover-up that spanned four U.S. Presidents pushed the country's first female newspaper publisher and a hard-driving editor to join an unprecedented battle between journalist and government. Inspired by true events.\",\"release_date\":\"2017-12-22\"}]}";

    private ProgressBar progressBar;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        setUpRecyclerView();

        loadMovieData();
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_thumbnails);
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        recyclerView.setHasFixedSize(true);
    }

    private void setUpAdapter(String response) {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, movieCount, response);
        movieRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    private void renderMovies(String response) {
        movieCount = MovieJSONUtils.getMovieCount(response);
        setUpAdapter(response);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Clicked on: " + position + " position", Toast.LENGTH_SHORT).show();
    }

    private void loadMovieData() {
        new FetchMoviePosterTask().execute();
    }

    public class FetchMoviePosterTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            showLoadingIndicator();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            URL movieDBURL = NetworkUtils.buildUrl(getString(R.string.TMDB_API_KEY));
            Log.d("MovieDBURL", movieDBURL.toString());

            try {
                return NetworkUtils
                        .getResponseFromHttpUrl(movieDBURL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            hideLoadingIndicator();
            renderMovies(jsonResponse);
            super.onPostExecute(jsonResponse);
        }
    }

    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
