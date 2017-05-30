        ORG C 10
        LD R0,@A
        TSF R1,INPR
        CAL X
        ST R2,@B
        HLT
X:      NOT R1
        INC R1,R1
        ADD R2,R0,R1
        RET
        ORG D 3
A:      DEC 11
B:      BIN 1100
        END
