#include <stdio.h>
#include <curl/curl.h>

int main(void)
{
    CURL *curl;
    CURLcode res;

    printf("Devlinux\n");
    curl = curl_easy_init();
    if (curl) {
        printf("Build dependency check: OK\n");
        curl_easy_cleanup(curl);
    }

    return 0;
}
