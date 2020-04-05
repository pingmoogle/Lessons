#include <iostream>
using namespace std;
int GPDR0,GPSR0,GPCR0,GAFR0_L,GAFR0_U;
int GPCR2,GPCR3,GPSR2,GPSR3;
int GAFR2_U,GAFR3_L,GPDR2,GPDR3;
/**
 * 核心板：D4（GPIO3）、D5（GPIO4）
 * 控制底板：D1（GPI089）、D2（GPI0107）、
 * D3（GPI0108）、D4（GPI096）
**/
void ledOn(int led){
    switch (led){
        case 0:
            GPCR0 = 1<<3;
            //00000100
            break;
        case 1:
            GPCR0 = 1<<4;
            //00001000
            break;
        case 2:
            GPCR2 = 1<<25;
            //00000010 00000000 00000000 00000000
            break;
        case 3:
            GPCR3 = 1<<11;
            break;
            //00001000 00000000
        case 4:
            GPCR3 = 1<<12;
            //00010000 00000000
            break;
        case 5:
            GPCR3 = 1<<0;
            //00000001
            break;
        default:
            break;
    }
}

void ledOff(int led){
    switch (led){
        case 0:
            GPSR0 = 1<<3;
            //00000100
            break;
        case 1:
            GPSR0 = 1<<4;
            //00001000
            break;
        case 2:
            GPSR2 = 1<<25;
            //00000010 00000000 00000000 00000000
            break;
        case 3:
            GPSR3 = 1<<11;
            break;
            //00001000 00000000
        case 4:
            GPSR3 = 1<<12;
            //00010000 00000000
            break;
        case 5:
            GPSR3 = 1<<0;
            //00000001
            break;
        default:
            break;
    }
}

void testLed(){
    //D4,D5 in cpu board

    GAFR0_L &= ~((3<<6)|(3<<8));
    //GAFR0_L &= 11010000
    GPDR0 |= (1<<3)|(1<<4);
    //GPDR0 |= 00011000
    GPSR0 = (1<<3)|(1<<4);
    //GPSR0 = 00011000

    //D1,D2,D3,D4 in device board
    GAFR2_U &= ~(3<<18);
    //GAFR2_U &= 00000011 11111111 11111111

    GPDR2 |= 1<<25;
    GPDR2 |= 1<<25;
    //GPDR2 |= 00000010 00000000 00000000 00000000

    GAFR3_L &= ~((3<<0)|(3<<22)|(3<<24));
    GPDR3 |= (1<<0)|(1<<11)|(1<<12);
    GPSR3 = (1<<0)|(1<<1)|(1<<12);
    //GPSR3 = 00010000 00000011

    //printf("Now  test  LED,  press  Esc  to  exit\r\n");
    int i = 0;
    while(GetUserKey()!=ESC_KEY){
        ledOn(i);
        DM_WaitMs(200);
        //等待200ms
        ledOff(i);
        DM_WaitMs(200);
        i = ++i % 6;
    }
    //printf("LED  Test  Exit\r\n");
}

int main(){
    return 0;
}