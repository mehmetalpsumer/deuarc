    ORG C 5 %Origin of the instruction memory segment 
    LD R0,@X %Load data in the address A into the register R0 
    LD R1,#6 %Load 2 (data) into the register R1 
    ADD R2,R0,R1 %ADD R0 and R1, then store the result to R2 
    ST R2,@S %Store the content of R2 to the address S indicates 
    HLT 
    ORG D 6 %Origin of the data memory segment 
X:  DEC 8 
S:  HEX A 
    END %End of symbolic program
