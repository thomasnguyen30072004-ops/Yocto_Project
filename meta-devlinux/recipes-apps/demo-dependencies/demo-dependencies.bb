DESCRIPTION = "Demo program showcasing package dependencies and splitting"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}"
SRC_URI = "file://depend_buildtime.c \
           file://depend_runtime.c \
           file://package_split.c \
           file://Makefile"

DEPENDS = "curl"
RDEPENDS:${PN} = "bash"
RDEPENDS:package-split = "bash"

EXTRA_OEMAKE = "CC='${CC}' CFLAGS='${CFLAGS} -Wl,--hash-style=gnu'"

# Khai báo các package con
PACKAGES =+ "demo-tools package-split"
ALLOW_EMPTY:${PN} = "1"
# Gộp depend_buildtime và depend_runtime vào package demo-tools
FILES:demo-tools = "${bindir}/depend_buildtime \
                    ${bindir}/depend_runtime"

# Khai báo package riêng cho package_split
FILES:package-split = "${bindir}/package_split"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 depend_buildtime ${D}${bindir}/depend_buildtime
    install -m 0755 depend_runtime ${D}${bindir}/depend_runtime
    install -m 0755 package_split ${D}${bindir}/package_split
}

