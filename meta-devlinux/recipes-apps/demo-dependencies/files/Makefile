CC ?= $(CC)
CFLAGS ?= $(CFLAGS)

all: depend_buildtime depend_runtime package_split

depend_buildtime: depend_buildtime.c
	$(CC) $(CFLAGS) depend_buildtime.c -lcurl -o depend_buildtime

depend_runtime: depend_runtime.c
	$(CC) $(CFLAGS) depend_runtime.c -o depend_runtime

package_split: package_split.c
	$(CC) $(CFLAGS) package_split.c -o package_split

clean:
	rm -f depend_buildtime depend_runtime package_split

