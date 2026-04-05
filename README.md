# Soul Devil Network Tool 🔥

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android-brightgreen)
![Min SDK](https://img.shields.io/badge/Min%20SDK-28-blue)
![Target SDK](https://img.shields.io/badge/Target%20SDK-35-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

**A powerful network analysis and packet capture tool for Android devices**

Monitor network connections • Capture packets • Analyze DNS queries • Track traffic statistics

</div>

---

## 📋 Table of Contents

- [Features](#-features)
- [Why Soul Devil Network Tool?](#-why-soul-devil-network-tool)
- [Comparison with Other Tools](#-comparison-with-other-tools)
- [System Requirements](#-system-requirements)
- [Setup Instructions](#-setup-instructions)
  - [Ubuntu/Linux](#ubuntu--linux)
  - [macOS](#macos)
  - [Windows](#windows)
- [Building the App](#-building-the-app)
- [How to Use](#-how-to-use)
- [Architecture](#-architecture)
- [Technologies Used](#-technologies-used)
- [Troubleshooting](#-troubleshooting)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### 🌐 Network Monitoring
- **Real-time Connection Tracking**: Monitor active network connections with detailed information
- **Per-App Traffic Analysis**: See which apps are using your network
- **Protocol Detection**: Identify TCP, UDP, ICMP, and other protocols
- **IP Address Resolution**: Track source and destination IPs

### 📦 Packet Capture
- **PCAP File Generation**: Export network traffic in industry-standard PCAP format
- **VPN-based Capture**: Non-root packet capture using VPN service
- **Deep Packet Inspection**: Parse and analyze packet headers and payloads
- **Wireshark Compatible**: Open captured files in Wireshark for advanced analysis

### 🔍 DNS Analysis
- **DNS Query Logging**: Track all DNS resolutions
- **Query Type Detection**: A, AAAA, CNAME, MX, and more
- **TTL Monitoring**: View Time-To-Live for cached records
- **Blocked Domain Detection**: Identify blocked or filtered domains

### 📊 Traffic Statistics
- **Data Usage Graphs**: Visualize upload/download trends
- **Connection Statistics**: Count active and total connections
- **Bandwidth Monitoring**: Real-time throughput tracking
- **Historical Data**: View traffic patterns over time

### ⚙️ Advanced Features
- **Foreground Service**: Reliable background operation
- **Edge-to-Edge UI**: Modern Android 15 design
- **Scoped Storage**: Secure file handling on Android 10+
- **Boot Receiver**: Optional auto-start on device boot
- **Material Design 3**: Beautiful, intuitive interface

---

## 🎯 Why Soul Devil Network Tool?

### 🔐 **Privacy First**
- **No Root Required**: Works on any Android device (Android 9+)
- **Local Processing**: All data stays on your device
- **No Cloud Dependencies**: Complete offline functionality
- **Open Source**: Transparent and auditable code

### 💪 **Powerful Analysis**
- Industry-standard PCAP format for professional analysis
- Integration with Wireshark and other network tools
- Detailed per-app network insights
- DNS query tracking for privacy auditing

### 🚀 **Performance**
- Efficient VPN-based packet capture
- Minimal battery impact
- Optimized database queries with Room
- Background processing with WorkManager

### 🎨 **User Experience**
- Clean Material Design interface
- Intuitive bottom navigation
- Real-time updates with LiveData
- Dark mode support

---

## 📊 Comparison with Other Tools

| Feature | Soul Devil | NetGuard | PCAPdroid | PacketCapture |
|---------|------------|----------|-----------|---------------|
| **No Root Required** | ✅ | ✅ | ✅ | ✅ |
| **PCAP Export** | ✅ | ❌ | ✅ | ✅ |
| **Per-App Stats** | ✅ | ✅ | ✅ | ❌ |
| **DNS Logging** | ✅ | ✅ | ✅ | ❌ |
| **Material Design 3** | ✅ | ❌ | ⚠️ | ❌ |
| **Android 15 Support** | ✅ | ⚠️ | ✅ | ⚠️ |
| **Traffic Graphs** | ✅ | ❌ | ⚠️ | ❌ |
| **Open Source** | ✅ | ✅ | ✅ | ❌ |
| **Database Storage** | ✅ (Room) | ❌ | ⚠️ | ❌ |
| **Active Development** | ✅ | ⚠️ | ✅ | ❌ |

**Legend**: ✅ Full Support | ⚠️ Partial Support | ❌ Not Available

---

## 💻 System Requirements

### Android Device
- **Minimum SDK**: Android 9.0 (API 28)
- **Target SDK**: Android 15 (API 35)
- **Recommended**: Android 11+ for best experience
- **Storage**: ~50 MB for app + space for PCAP files
- **Permissions**: VPN, Notifications, Storage (on Android 9)

### Development Environment
- **Android Studio**: Jellyfish (2023.3.1) or later
- **JDK**: Java 17 (OpenJDK recommended)
- **Gradle**: 8.5+ (included in wrapper)
- **Build Tools**: Android SDK Build Tools 35.0.0

---

## 🛠️ Setup Instructions

### Ubuntu / Linux

#### 1. Install Java 17
```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version  # Verify installation
```

#### 2. Install Android Studio
```bash
# Download Android Studio
wget https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2023.3.1.18/android-studio-2023.3.1.18-linux.tar.gz

# Extract
tar -xzf android-studio-*-linux.tar.gz

# Move to /opt
sudo mv android-studio /opt/

# Run Android Studio
/opt/android-studio/bin/studio.sh
```

#### 3. Configure Android SDK
- Open Android Studio
- Go to **Settings > Appearance & Behavior > System Settings > Android SDK**
- Install:
  - Android SDK Platform 35
  - Android SDK Build-Tools 35.0.0
  - Android Emulator
  - Android SDK Platform-Tools

#### 4. Clone and Build
```bash
# Clone repository
git clone https://github.com/vikrant-project/networking-app-source.git
cd networking-app-source

# Make gradlew executable
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Output: app/build/outputs/apk/debug/app-debug.apk
```

---

### macOS

#### 1. Install Java 17
```bash
# Using Homebrew
brew install openjdk@17

# Add to PATH
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Verify
java -version
```

#### 2. Install Android Studio
```bash
# Download using Homebrew Cask
brew install --cask android-studio

# Or download manually from:
# https://developer.android.com/studio
```

#### 3. Configure Android SDK
- Open Android Studio
- **Android Studio > Settings > Appearance & Behavior > System Settings > Android SDK**
- Install required SDK components (same as Linux)

#### 4. Clone and Build
```bash
# Clone repository
git clone https://github.com/vikrant-project/networking-app-source.git
cd networking-app-source

# Make gradlew executable
chmod +x gradlew

# Build
./gradlew assembleDebug
```

---

### Windows

#### 1. Install Java 17
- Download: [OpenJDK 17 for Windows](https://adoptium.net/temurin/releases/)
- Run installer and follow prompts
- Verify in Command Prompt:
```cmd
java -version
```

#### 2. Install Android Studio
- Download: [Android Studio for Windows](https://developer.android.com/studio)
- Run installer: `android-studio-2023.3.1.18-windows.exe`
- Follow installation wizard

#### 3. Configure Android SDK
- Open Android Studio
- **File > Settings > Appearance & Behavior > System Settings > Android SDK**
- Install SDK Platform 35 and Build Tools

#### 4. Clone and Build
```cmd
# Clone repository
git clone https://github.com/vikrant-project/networking-app-source.git
cd networking-app-source

# Build using Gradle wrapper (Windows)
gradlew.bat assembleDebug

# Output: app\build\outputs\apk\debug\app-debug.apk
```

---

## 🏗️ Building the App

### Using Android Studio (Recommended)

1. **Open Project**
   - Launch Android Studio
   - Click **File > Open**
   - Navigate to `networking-app-source` directory
   - Click **OK**

2. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for "Gradle sync finished" message

3. **Build APK**
   - **Build > Build Bundle(s) / APK(s) > Build APK(s)**
   - Or click the hammer icon 🔨

4. **Run on Device/Emulator**
   - Connect Android device with USB debugging enabled
   - Click **Run** ▶️ button
   - Select target device

### Using Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build (requires signing key)
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug

# Run all tests
./gradlew test

# Clean build
./gradlew clean build
```

### Build Variants
- **Debug**: `app-debug.apk` - For development and testing
- **Release**: `app-release.apk` - For production (requires signing)

---

## 📱 How to Use

### 1. **First Launch**
- Grant VPN permission when prompted
- Grant notification permission (Android 13+)
- The app doesn't create a real VPN tunnel - it only intercepts local traffic

### 2. **Start Capturing**
- Tap the **Play** ▶️ floating action button
- VPN icon will appear in status bar
- Notification shows capture is active

### 3. **Monitor Connections**
- **Connections Tab** 🔗: View active network connections
  - See app name, IP addresses, ports, protocol
  - Track data sent/received per connection
- **DNS Tab** 🌐: Monitor DNS queries
  - Domain names, resolved IPs, query types
  - TTL values and blocking status
- **Stats Tab** 📊: View traffic statistics
  - Upload/download graphs
  - Total data usage
  - Connection counts
- **PCAP Tab** 📁: Manage captured files
  - List of all PCAP files
  - Share files for external analysis
  - Delete old captures

### 4. **Stop Capturing**
- Tap the **Stop** ⏹️ button in notification
- Or tap the FAB again in the app
- PCAP file is automatically saved

### 5. **Analyze PCAP Files**
- Tap a PCAP file in the PCAP tab
- Choose **Share** to export
- Open in Wireshark or other tools:
  ```bash
  # On desktop with Wireshark installed
  wireshark captured_file.pcap
  ```

### 6. **Settings**
- Access via ⚙️ menu in toolbar
- Configure auto-start on boot
- Clear all data
- Set capture buffer size

---

## 🏛️ Architecture

```
Soul Devil Network Tool
│
├── 📱 Presentation Layer
│   ├── MainActivity (Bottom Navigation, FAB)
│   ├── ConnectionsFragment (RecyclerView + Adapter)
│   ├── DnsFragment (RecyclerView + Adapter)
│   ├── StatsFragment (MPAndroidChart)
│   ├── PcapFragment (File list)
│   └── SettingsFragment (Preferences)
│
├── 🧠 ViewModel Layer (MVVM)
│   ├── ConnectionsViewModel (LiveData)
│   ├── DnsViewModel (LiveData)
│   ├── StatsViewModel (LiveData)
│   └── PcapViewModel (LiveData)
│
├── 💾 Data Layer
│   ├── Room Database
│   │   ├── AppDatabase (Singleton)
│   │   ├── ConnectionDao
│   │   └── DnsDao
│   ├── Entities
│   │   ├── ConnectionRecord
│   │   └── DnsRecord
│   └── Repositories
│       ├── ConnectionRepository
│       └── PcapRepository
│
├── 🛡️ Service Layer
│   ├── CaptureService (VpnService)
│   ├── PacketParser (IP/TCP/UDP/DNS)
│   └── PcapWriter (PCAP format)
│
└── 🔧 Utilities
    ├── IpUtils (IP parsing)
    ├── ProtocolUtils (Protocol detection)
    └── FileUtils (File operations)
```

### Design Patterns
- **MVVM**: Separation of UI and business logic
- **Repository Pattern**: Abstracted data sources
- **Singleton**: Database instance management
- **Observer Pattern**: LiveData for reactive UI
- **Dependency Injection**: Manual DI (can migrate to Hilt/Dagger)

---

## 🔧 Technologies Used

### Android Jetpack
- **Room** 2.6.1 - SQLite database with compile-time verification
- **LiveData** 2.8.2 - Lifecycle-aware observable data
- **ViewModel** 2.8.2 - UI-related data storage
- **Navigation** 2.7.7 - Fragment navigation
- **WorkManager** 2.9.0 - Background task scheduling
- **Preference** 1.2.1 - Settings UI

### UI Components
- **Material Design 3** 1.12.0 - Modern UI components
- **ConstraintLayout** 2.1.4 - Flexible layouts
- **MPAndroidChart** 3.1.0 - Traffic graphs and charts
- **Glide** 4.16.0 - Image loading for app icons

### Network
- **VpnService** - Android VPN API for packet capture
- **ParcelFileDescriptor** - Low-level network interface access

### Build System
- **Gradle** 8.5 - Build automation
- **Android Gradle Plugin** 8.5.2 - Android-specific build tasks

---

## 🐛 Troubleshooting

### Common Issues

#### ❌ **App crashes on launch**
**Solution**: Check Android version (minimum Android 9)
```bash
# View crash logs
adb logcat | grep "Soul Devil"
```

#### ❌ **VPN permission denied**
**Solution**: Grant VPN permission manually
- Settings > Apps > Soul Devil > Permissions
- Enable VPN permission

#### ❌ **Capture not starting**
**Symptoms**: FAB shows play button, but nothing happens
**Solutions**:
1. Revoke VPN permission and grant again
2. Restart app completely
3. Check notification permission (Android 13+)

#### ❌ **PCAP files empty**
**Solution**: Ensure you have network activity while capturing
- Open browser or other apps
- Check storage permissions

#### ❌ **Build failed: SDK not found**
**Solution**: Configure SDK location
```bash
# Create local.properties
echo "sdk.dir=/path/to/Android/Sdk" > local.properties
```

#### ❌ **Gradle sync failed**
**Solutions**:
1. Update Gradle wrapper: `./gradlew wrapper --gradle-version=8.5`
2. Invalidate caches: **File > Invalidate Caches / Restart**
3. Delete `.gradle` folder and sync again

#### ❌ **App not compatible with device**
**Check**: Device must run Android 9 (API 28) or higher
```bash
# Check device Android version
adb shell getprop ro.build.version.sdk
```

---

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### Reporting Bugs
1. Check existing [Issues](https://github.com/vikrant-project/networking-app-source/issues)
2. Create new issue with:
   - Device model and Android version
   - Steps to reproduce
   - Crash logs (if applicable)
   - Expected vs actual behavior

### Feature Requests
1. Open an issue with `[Feature Request]` tag
2. Describe the feature and use case
3. Explain why it would be valuable

### Pull Requests
1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open Pull Request

### Code Style
- Follow Android Kotlin/Java style guide
- Use meaningful variable names
- Add comments for complex logic
- Update README if adding features

---

## 📄 License

This project is licensed under the MIT License.

```
MIT License

Copyright (c) 2025 Vikrant Project

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 🙏 Acknowledgments

- **Android Open Source Project** - Core Android platform
- **Material Design** - Design system and components
- **PhilJay/MPAndroidChart** - Chart library
- **Wireshark** - PCAP format specification
- **Android Developer Community** - Tutorials and support

---

## 📞 Contact & Support

- **GitHub Issues**: [Report bugs or request features](https://github.com/vikrant-project/networking-app-source/issues)
- **Project Repository**: [github.com/vikrant-project/networking-app-source](https://github.com/vikrant-project/networking-app-source)

---

<div align="center">

**Made with ❤️ for Android Network Analysis**

⭐ Star this repo if you find it useful!

[Report Bug](https://github.com/vikrant-project/networking-app-source/issues) · [Request Feature](https://github.com/vikrant-project/networking-app-source/issues) · [Documentation](https://github.com/vikrant-project/networking-app-source/wiki)

</div>
