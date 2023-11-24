#include <stdio.h>
int main()
{

    unsigned char name[100];
    unsigned char addr[100];
    char ch = '0';
    int i;
    gets(name);
    gets(addr);
    puts(name);
   strcat(name, addr);
    puts(name);
    return 0;
}