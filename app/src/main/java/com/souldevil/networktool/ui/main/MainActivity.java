package com.souldevil.networktool.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.souldevil.networktool.R;
import com.souldevil.networktool.databinding.ActivityMainBinding;
import com.souldevil.networktool.service.CaptureService;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private boolean isCapturing = false;

    private ActivityResultLauncher<Intent> vpnPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    startCaptureService();
                } else {
                    Toast.makeText(this, "VPN permission denied", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge for Android 15+
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        setSupportActionBar(binding.toolbar);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Setup Bottom Navigation
        BottomNavigationView navView = binding.bottomNavView;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_connections, R.id.navigation_dns,
                R.id.navigation_stats, R.id.navigation_pcap)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Setup FAB
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(v -> toggleCapture());

        // Observe capturing state
        viewModel.getIsCapturing().observe(this, capturing -> {
            isCapturing = capturing;
            updateFabIcon();
        });

        // Request permissions
        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        }
    }

    private void toggleCapture() {
        if (isCapturing) {
            stopCapture();
        } else {
            startCapture();
        }
    }

    private void startCapture() {
        // Check VPN permission
        Intent vpnIntent = VpnService.prepare(this);
        if (vpnIntent != null) {
            vpnPermissionLauncher.launch(vpnIntent);
        } else {
            startCaptureService();
        }
    }

    private void startCaptureService() {
        Intent intent = new Intent(this, CaptureService.class);
        intent.setAction(CaptureService.ACTION_START);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        
        viewModel.setCapturing(true);
        Toast.makeText(this, "Capture started", Toast.LENGTH_SHORT).show();
    }

    private void stopCapture() {
        Intent intent = new Intent(this, CaptureService.class);
        intent.setAction(CaptureService.ACTION_STOP);
        startService(intent);
        
        viewModel.setCapturing(false);
        Toast.makeText(this, "Capture stopped", Toast.LENGTH_SHORT).show();
    }

    private void updateFabIcon() {
        if (isCapturing) {
            binding.fab.setImageResource(R.drawable.ic_stop);
            binding.fab.setBackgroundTintList(
                    ContextCompat.getColorStateList(this, R.color.red_accent));
        } else {
            binding.fab.setImageResource(R.drawable.ic_play);
            binding.fab.setBackgroundTintList(
                    ContextCompat.getColorStateList(this, R.color.primary));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
            navController.navigate(R.id.navigation_settings);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
