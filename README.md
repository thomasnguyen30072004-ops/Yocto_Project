# 🧩 Yocto Project – Custom Embedded Linux for Raspberry Pi 4

## 📌 Overview

This project demonstrates how to build a **custom embedded Linux distribution** using the Yocto Project.
The system is configured to run on **Raspberry Pi 4**, with custom layers, applications, and kernel modifications.

The project covers:

* Building a minimal Linux image
* Creating and managing Yocto layers
* Adding custom applications (hello-world, devtool-based apps)
* Handling package dependencies
* Using `devtool` for development workflow
* Generating SDK for cross-development
* Customizing Linux kernel (GPIO module)

---

## ⚙️ System Requirements

### 💻 Hardware

* CPU: Quad-core or higher
* RAM: Minimum 8GB (Recommended: 16GB)
* Storage: ≥ 100GB (SSD recommended)

### 🐧 Software

* OS: Ubuntu 20.04 / 22.04 (recommended)
* Required packages:

```bash
sudo apt-get update
sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib \
     build-essential chrpath socat cpio python3 python3-pip python3-pexpect \
     xz-utils debianutils iputils-ping libsdl1.2-dev xterm vim
```

---

## 📥 Setup Yocto Environment

```bash
mkdir ~/yocto
cd ~/yocto

git clone git://git.yoctoproject.org/poky
cd poky
git checkout dunfell
```

---

## 🏗️ Build Configuration

### Initialize build environment

```bash
source oe-init-build-env
```

### Modify `local.conf`

```conf
MACHINE ?= "raspberrypi4"
INHERIT += "rm_work"
PACKAGE_CLASSES ?= "package_rpm"

# Add custom packages
IMAGE_INSTALL_append = " hello-world hello-devtool"
```

---

### Modify `bblayers.conf`

Add required layers:

```conf
BBLAYERS ?= " \
  ${TOPDIR}/../poky/meta \
  ${TOPDIR}/../poky/meta-poky \
  ${TOPDIR}/../poky/meta-yocto-bsp \
  ${TOPDIR}/../meta-openembedded/meta-oe \
  ${TOPDIR}/../meta-raspberrypi \
  ${TOPDIR}/../meta-devlinux \
"
```

---

## 🧱 Build Image

```bash
bitbake core-image-minimal
```

Output image location:

```bash
build/tmp/deploy/images/raspberrypi4/
```

---

## ▶️ Run on QEMU (Testing)

```bash
runqemu qemux86-64 nographic
```

---

## 🍓 Flash to Raspberry Pi 4

### Decompress image

```bash
bzip2 -d image.wic.bz2
```

### Flash to SD card

```bash
sudo dd if=image.wic of=/dev/sdX bs=4M status=progress
sync
```

---

## 🧩 Custom Layer: `meta-devlinux`

### Create layer

```bash
bitbake-layers create-layer ../meta-devlinux
bitbake-layers add-layer ../meta-devlinux
```

---

## 👨‍💻 Custom Application: Hello World

### Recipe

```bash
meta-devlinux/recipes-apps/hello-world/hello-world.bb
```

### Run on target

```bash
hello-world
```

Output:

```
Devlinux: hello world!
```

---

## 🛠️ Devtool Workflow

### Add new recipe

```bash
devtool add hello-devtool <git-repo-url>
```

### Build

```bash
devtool build hello-devtool
```

### Deploy to target

```bash
devtool deploy-target hello-devtool root@<ip>
```

---

## 📦 Package Dependencies

### Build-time dependency

```conf
DEPENDS = "curl"
```

### Run-time dependency

```conf
RDEPENDS_${PN} = "bash"
```

---

## 📦 Package Splitting

```conf
PACKAGES =+ "demo-tools package-split"

FILES_demo-tools = "${bindir}/depend_buildtime ${bindir}/depend_runtime"
FILES_package-split = "${bindir}/package_split"
```

---

## 🧰 SDK Generation

### Standard SDK

```bash
bitbake core-image-minimal -c populate_sdk
```

### Extensible SDK

```bash
bitbake core-image-minimal -c populate_sdk_ext
```

### Setup SDK environment

```bash
source /opt/poky/<version>/environment-setup-*
```

---

## 🧠 Kernel Customization (GPIO Module)

### Features:

* Custom Linux kernel module
* Controls GPIO (LED)
* Auto-load at boot

### Steps:

1. Modify kernel source:

```bash
devtool modify virtual/kernel
```

2. Add module (mgpio)

3. Create patch:

```bash
git format-patch -1
```

4. Add `.bbappend`:

```conf
SRC_URI += "file://mgpio.patch"
KERNEL_MODULE_AUTOLOAD += "mgpio"
```

5. Build kernel:

```bash
bitbake virtual/kernel
```

---

## 📁 Project Structure

```bash
yocto-project/
├── build/
│   └── conf/
├── meta-devlinux/
├── scripts/
├── README.md
└── .gitignore
```

---

## 🚀 Key Achievements

* ✅ Built custom Linux image using Yocto
* ✅ Integrated Raspberry Pi 4 support
* ✅ Created custom layer and recipes
* ✅ Implemented package dependency handling
* ✅ Used devtool for rapid development
* ✅ Generated SDK for cross-compilation
* ✅ Customized Linux kernel with GPIO module

---

## 📚 References

* Yocto Project Documentation
* Embedded Linux Development Guides

---

## 👤 Author

**Thomas Nguyen**

---

## 📜 License

This project is for educational and research purposes.
