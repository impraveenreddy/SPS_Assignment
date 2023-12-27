package com.sps.spsassignment

import ApiClient
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        apiService = ApiClient.apiService

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = MovieAdapter(emptyList()) { movie ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchMovies("")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchMovies(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change
                return false
            }
        })

        // Add OnActionExpandListener
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // SearchView expanded
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // SearchView collapsed (closed)
                fetchMovies("") // Fetch movies with an empty query
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun fetchMovies(query: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = if (query.isNullOrEmpty()) {
                apiService.getTrendingMovies("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOGUxYjhlMTEyZWYxOTFlNTJkZjUzMmU2MDEyNTc3YSIsInN1YiI6IjY1ODE3ZDk4ZGY4NmE4MDkzN2U3ZGRlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yp6UnW-cAnTz0QnXsKoJbIFA4JM_CO6SfoYKpXLP1t0")
            } else {
                apiService.searchMovies("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOGUxYjhlMTEyZWYxOTFlNTJkZjUzMmU2MDEyNTc3YSIsInN1YiI6IjY1ODE3ZDk4ZGY4NmE4MDkzN2U3ZGRlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yp6UnW-cAnTz0QnXsKoJbIFA4JM_CO6SfoYKpXLP1t0", query)
            }

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    adapter = MovieAdapter(movies) { movie ->
                        val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                        intent.putExtra("movie", movie)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Error fetching movies", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

/*class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService = ApiClient.apiService

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        // Set up RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = MovieAdapter(emptyList()) { movie ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Fetch trending movies
        fetchMovies("")

        // Set up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fetchMovies(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchMovies(query: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = if (query.isNullOrEmpty()) {
                apiService.getTrendingMovies("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOGUxYjhlMTEyZWYxOTFlNTJkZjUzMmU2MDEyNTc3YSIsInN1YiI6IjY1ODE3ZDk4ZGY4NmE4MDkzN2U3ZGRlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yp6UnW-cAnTz0QnXsKoJbIFA4JM_CO6SfoYKpXLP1t0")
            } else {
                apiService.searchMovies("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOGUxYjhlMTEyZWYxOTFlNTJkZjUzMmU2MDEyNTc3YSIsInN1YiI6IjY1ODE3ZDk4ZGY4NmE4MDkzN2U3ZGRlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yp6UnW-cAnTz0QnXsKoJbIFA4JM_CO6SfoYKpXLP1t0", query)
            }

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    adapter = MovieAdapter(movies) { movie ->
                        val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                        intent.putExtra("movie", movie)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Error fetching movies", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}*/

