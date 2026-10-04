# How to run the code
There are steps to this process before running the code for the robot
## In Android Studio
First, you need to install git to get the repositories

### Windows

```powershell
winget install --id Git.Git -e --source winget
```

### Mac (via Homebrew)
```zsh
brew install git
```

### Debian / Ubuntu / Linux Mint / Kali Linux / Pop!_OS
```bash
sudo apt install git
```

### Fedora / RHEL / CentOS / Rocky Linux
```bash
sudo dnf install git
```

### Arch Linux / Manjaro / EndeavourOS
```bash
sudo pacman -S git
```
(Note: Git is also available through the AUR)

### openSUSE / SUSE / Linux Enterprise
```bash
sudo zypper install git
```

### Gentoo Linux / Other Linux compiled binaries
```bash
sudo emerge --ask dev-vcs/git
```
___
You are now ready to clone the repository:

- Open Android Studio → **File** → **New** → **Project from Version Control**
- URL: `https://github.com/hakiManCode/36774-pedropathing2026.git` → **Clone**

### When there is an update:
```bash
git pull
```

### In case you accidentally edit anything or it says an error about local changes:
```bash
git checkout -- .
```

## Preparing the robot for driving

1. Turn the robot on and plug the laptop into the Control Hub with a USB-C cable
2. In Android Studio, click the green **Run** button (should look like a play button) at the top and wait for "Install successfully finished."
3. Unplug the Robot, and set it in a clear area

## Running an autonomous

1. On the Driver Station, confirm the active configuration is **firstconfig** and ensure the Driver Station is connected to the Robot and its Wi-Fi.
2. Pick the auto from the autonomous list (left dropdown), tap **INIT**, then **Play**.
3. Keep clear space around the robot and a thumb on **Stop**.
