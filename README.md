## How to build

git clone git://git.yoctoproject.org/poky -b kirkstone
cd poky

# copy conf từ repo này vào build/conf

source oe-init-build-env
bitbake core-image-minimal
