#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define INPUT_SIZE 50
void checkSet(int input[], int input_length);
int findIntersection(int input1[], int input1_length, int input2[], int input2_length);
float findUnion(int input1[], int input1_length , int input2[], int input2_length);
void calculateJaccard(int input1[], int input1_length, int input2[], int input2_length);
int main(void){
    int n1,n2;
    printf("Input first set length: ");
    scanf("%d",&n1);
    int arr1[n1];
    char input1[n1 + 1];
    printf("Input first set: ");
    scanf("%s",input1);
    checkSet(arr1,n1);

    char* token = strtok(input1, ",");
    int i = 0;
    while (token != NULL) {
        arr1[i] = atoi(token);
        token = strtok(NULL, ",");
        i++;
    }

    printf("Input set length 2: ");
    scanf("%d",&n2);
    int arr2[n2];
    char input2[n2 +1];
    printf("Input second set: ");
    scanf("%s",input2);
    checkSet(arr2,n2);


    char* token2 = strtok(input2, ",");
    i = 0;
    while (token2 != NULL) {
        arr2[i] = atoi(token2);
        token2 = strtok(NULL, ",");
        i++;
    }

    if(n1 != 0 && n2 != 0)
        calculateJaccard(arr1,n1,arr2,n2);
}

void checkSet(int input[], int input_length){
    if(input_length == 0){
        printf("Set cannot be empty\n");
        exit(0);
    }
}

int findIntersection(int input1[], int input1_length, int input2[], int input2_length){
    int count = 0;
    if(input1_length >= input2_length){
        for(int i = 0; i < input2_length; i++){
            for(int j = 0; j < input1_length; j++){
                if(input2[i] == input1[j])
                    count++;
            }
        }
    }
    else{
        for(int i = 0; i < input1_length; i++){
            for(int j = 0; j < input2_length; j++){
                if(input1[i] == input2[j])
                    count++;
            }
        }
    }
    return count;
        
    
}
float findUnion(int input1[], int input1_length , int input2[], int input2_length){
    int common = findIntersection(input1,input1_length,input2,input2_length);
    float count = input1_length + input2_length - common;
    return count;
}
void calculateJaccard(int input1[], int input1_length, int input2[], int input2_length){
    float result = findIntersection(input1,input1_length,input2,input2_length) / findUnion(input1,input1_length,input2,input2_length);
    printf("Jaccard similarity is %f .\n",result);
}