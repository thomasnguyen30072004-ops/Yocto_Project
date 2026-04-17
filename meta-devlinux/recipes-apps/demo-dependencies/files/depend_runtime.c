#include <stdio.h>

int main(void)
{
    printf("Devlinux\n");
    int status = system("bash -c 'echo This is executed by bash at runtime!'");
    if (status == -1) {
        perror("Failed to run bash command");
            return 1;
    }

    return 0;
}
