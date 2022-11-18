#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX 50
typedef struct StudentRecord
{
    int sid;
    char name[10];
    char course[10];
} StudentRecord;


int main(void){
    FILE * fptr1 = fopen("input_p1_ex1.txt","r");
    FILE * fptr2 = fopen("input_p1_ex2.txt","r");
    FILE * fptr3 = fopen("StudentRecords.csv","r+");
    char buffer[MAX];
    char* token = malloc(sizeof(char) * MAX);
    
    StudentRecord * records = malloc(sizeof(StudentRecord) * MAX);
    int counter = 0;
    int searchID = 0;

    if(fptr1 == NULL || fptr2 == NULL || fptr3 == NULL){
        printf("The file(s) cannot be opened\n");
    }

    
    else{
        // Store all records in StudentRecord array
        while(fgets(buffer,sizeof(buffer),fptr3)){
            token = strtok(buffer,",");
            records[counter].sid = atoi(token);
            token = strtok(NULL,",");
            strcpy(records[counter].name,token);
            token = strtok(NULL,",");
            strcpy(records[counter].course,token);
            counter++;
        }
        
        while(fgets(buffer,sizeof(buffer),fptr1)){
            //Case 1
            if(buffer[0] == '1' && buffer[1] == '\n'){
                while(fgets(buffer,sizeof(buffer),fptr1) && buffer[0] != '2' && buffer[0] != '3' && buffer[0] != '4' && buffer[0] != '5'){
                    counter++;
                    token = strtok(buffer," ");
                    records[counter].sid = atoi(token);
                    token = strtok(NULL,",");
                    strcpy(records[counter].name,token);
                    token = strtok(NULL,",");
                    strcpy(records[counter].course,token);
                    printf("A record has been inserted into the StudentRecords file.\n");
                }
            }

            //Case 2
            if(buffer[0] == '2' && buffer[1] == '\n'){
                int found = 0;
                fgets(buffer,sizeof(buffer),fptr1);
                searchID = atoi(strtok(buffer,"\n"));
                printf("Searching for a student with student id: %d\n",searchID);
                printf("\nThe following records were found for a student with student id:%d\n",searchID);
                for(int i = 0; i < counter; i++){
                    if(searchID == records[i].sid){
                        found = 1;
                        printf("%d %s %s\n",records[i].sid,records[i].name,records[i].course);
                    }
                }
                if(found == 0){
                    printf("No records found with the given student.\n");
                }
            }

            //Case 3
            if(buffer[0] == '3' && buffer[1] == '\n'){
                for(int i = 0; i < counter; i++){
                    printf("%d%10s%8s\n",records[i].sid,records[i].name,records[i].course);
                }
            }

            //Case 4
            if(buffer[0] == '4' && buffer[1] == '\n'){
                
                fgets(buffer,sizeof(buffer),fptr1);
                searchID = atoi(strtok(buffer,"\n"));
                printf("Removing records of students with sid: %d",searchID);
                printf("The contents of the records file after removal:\n");
                for(int i = 0; i < counter; i++){
                    if(searchID == records[i].sid){
                        records[i].sid = -1;
                    }
                    else{
                        printf("%d%10s%8s\n",records[i].sid,records[i].name,records[i].course);
                    }
                }
            }

            //Case 5
            if(buffer[0] == '5' && buffer[1] == '\n'){
                fgets(buffer,sizeof(buffer),fptr1);
                searchID = atoi(strtok(buffer," "));
                char * temp = strtok(NULL," ");
                temp = strtok(NULL," ");

                for(int i = 0; i < counter; i++){
                    if(searchID == records[i].sid){
                        strcpy(records[counter].course,temp);
                    }
                }
            }
        }
        //Close the files
        
        fclose(fptr1);
        fclose(fptr2);
        fclose(fptr3);
        
        
        //Print the records
        fptr3 = fopen("StudentRecords.csv","w+");
        for(int i = 0; i < counter; i++){
            if(records[i].sid != -1){
                fprintf(fptr3,"%d%10s%8s\n",records[i].sid,records[i].name,records[i].course);
            }
        }
        //Close file
        fclose(fptr3);
        
    }
    return 0;
}