#include <stdio.h>
#include <stdlib.h>
#include <string.h>
void printSortedMergedArray(int array1[],int lenArray1, int array2[], int lenArray2); 
int main(void){
    int n1,n2;
    printf("Input Size of Array 1: ");
    scanf("%d",&n1);
    int arr1[5] = {1,2,5,7,8};
    printf("Input Array 1: ");

    for(int n = 0; n < n1; n++){
        printf("%d ",arr1[n]);
    }

    printf("\nInput Size of Array 2:");
    scanf("%d",&n2);

    int arr2[4] = {1,3,4,6};
    printf("Input Array 2: ");
    for(int l = 0; l < n2; l++){
        printf("%d ",arr2[l]);
    }
    printf("\n");
    printSortedMergedArray(arr1,n1,arr2,n2);
    printf("\n");

}
void printSortedMergedArray(int array1[],int lenArray1, int array2[], int lenArray2){
    int i = 0;
    int j = 0;
    int k = 0;
    int result[lenArray1+lenArray2];
    
    while(i < lenArray1 && j < lenArray2){
        if(array1[i] < array2[j]){
            result[k] = array1[i];
            i++;
            k++;
        }
        else if(array1[i] > array2[j]){
            result[k] = array2[j];
            j++;
            k++;
        }
        else{
            result[k] = array1[i];
            result[k+1] = array1[i];
            k = k + 2;
            i++;
            j++;
        }
    }
    
    while(i < lenArray1){
        result[k] = array1[i];
        i++;
        k++;
    }
    while(j < lenArray2){
        result[k] = array2[j];
        j++;
        k++;
    }

    for(int w = 0; w < lenArray1 + lenArray2; w++){
        printf("%d ", result[w]);
    }

}

