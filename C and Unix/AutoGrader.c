#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
#include <math.h>


#define MAX_WORD_SIZE 50

void convertToLowercase(char *sPtr);
char** getUniqueWords(char** strs);
char** tokenize(char* str);
int ** ans2Vectors(char *instructor_answer, char *student_answer);

int lengthArr(int * arr){
    return sizeof(arr) / sizeof(arr[0]);
}

char ** getStudentAnswers(char ** questions, int n, int len){
    char **results = malloc(n*sizeof(char*));
    for (int x = 0; x < n; x++){
        results[x] = malloc(500*sizeof(char));
    }
    
    for(int i = 0; i < n; i++){
        if(questions[i] == NULL)
            break;
        printf("%s\n",questions[i]);
        scanf("%[^\n]s",results[i]);
        getchar();
    }
    return results;
}

bool stringCompare(char* aStr, char* bStr) {
    bool isTheSame = false;
    for (int i = 0; ; i++) {
        char a = aStr[i];
        char b = bStr[i];

        if (a == '\0' || b == '\0') {
            if (isTheSame) {
                if (a != b)
                    isTheSame = false;
            }
            break;
        }

        if (a == b)
            isTheSame = true;
    }
    return isTheSame;
}

int ** ans2Vectors(char *instructor_answer, char *student_answer){

    int** result = malloc(2*sizeof(int*));
    convertToLowercase(student_answer);
    convertToLowercase(instructor_answer);

    char** uniqueIns = getUniqueWords(tokenize(instructor_answer));
    char** instructorToken = tokenize(instructor_answer); 
    char** studentToken = tokenize(student_answer);
    
    // Get vector length
    int vecLen;
    for (vecLen = 0; ; vecLen++) {
        if (uniqueIns[vecLen] == NULL)
            break;
    }

    int insVector[vecLen];
    int stuVector[vecLen];
    for (int i = 0; i < vecLen; i++)
        insVector[i] = stuVector[i] = 0;

    for (int i = 0; ; i++) {
        char* iUnique = uniqueIns[i];

        if (iUnique == NULL)
            break;
            
        for (int j = 0; ; j++) {
            if (instructorToken[j] == NULL)
                break;

            if (stringCompare(instructorToken[j], iUnique)) {
                insVector[i]++;
            }
        }

        for (int j = 0; ; j++) {
            if (studentToken[j] == NULL)
                break;

            if (stringCompare(studentToken[j], iUnique)) {
                stuVector[i]++;
            }
        }
    }

    result[0] = malloc(sizeof(int) * vecLen);
    result[1] = malloc(sizeof(int) * vecLen);

    for (int i = 0; i < vecLen; i++) {
        result[0][i] = insVector[i];
        result[1][i] = stuVector[i];
    }

    return result;
}

double cosineSimilarity(char *instructor_answer, char *student_answer){
    double dotProduct = 0;
    double Ai2 = 0;
    double Bi2 = 0;
    double similarity = 0;
    int **vect = ans2Vectors(instructor_answer,student_answer);
    int vecLen;
    for (vecLen = 0; ; vecLen++) {
        if (vect[vecLen] == NULL)
            break;
    }

    for(int i = 0; i < vecLen;i++){ 
        dotProduct += vect[0][i] * vect[1][i];
    }

    for(int i = 0; i < vecLen;i++){ 
        Ai2 += vect[0][i] * vect[0][i];
    }
    Ai2 = sqrt(Ai2);

    for(int i = 0; i < vecLen;i++){ 
        Bi2 += vect[1][i] * vect[1][i];
    }
    Bi2 = sqrt(Bi2);

    similarity = dotProduct / (Ai2 * Bi2);
    return 10 * similarity;
}


void convertToLowercase(char *sPtr){
    for(; *sPtr != '\0'; *sPtr++){
        *sPtr = tolower(*sPtr);
    }
}

char** tokenize(char* str){

    size_t str_len = strlen(str);

    if (str_len == 0)
        return NULL;

    char** tokens = malloc(sizeof(char*) * 2);
    int token_size = 2;

    char word[MAX_WORD_SIZE] = "\0";
    int word_count = 0;
    for (register int i = 0, j = 0; i < str_len + 1; i++) {
        if (str[i] == ' ' || str[i] == ',' || str[i] == '.' || str[i] == '\0' || i == str_len) {
            size_t word_len = strlen(word);

            // Add a null character at the end
            if (word_len >= MAX_WORD_SIZE)
                word[j - 1] = '\0';
            else if (word_len > 0)
                word[j] = '\0';
            else { // Empty word
                j = 0;
                continue;
            }             

            // Grow size
            if (word_count >= token_size - 1) {
                token_size *= 2;
                tokens = realloc(tokens, sizeof(char*) * token_size);
            }

            tokens[word_count] = malloc(sizeof(char) * word_len + 1);
            strcpy(tokens[word_count++], word);

            j = 0;
            word[0] = '\0';
            continue;
        }

        if (j < MAX_WORD_SIZE - 1)
            word[j++] = str[i];
    }

    if (word_count == 0)
        tokens[1] = NULL;

    tokens[word_count] = NULL;

    return tokens;
}


/* Removes duplicates from an array of pointers to char arrays
 * Input: array of pointers to char arrays (containing duplicates)
 * Output: array of pointers to char array (duplicates removed) */
char** getUniqueWords(char** strs){

    char** uniques = malloc(sizeof(char*) * 2);
    int unique_size = 2;

    int unique_count = 0;

    for (register int i = 0; ; i++) {
        if (strs[i] == NULL)
            break;

        // Detect duplication
        bool is_duplicated = false;
        for (register int j = 0; j < unique_count; j++) {
            if (strcmp(strs[i], uniques[j]) == 0) {
                is_duplicated = true;
                break;
            }
        }

        if (!is_duplicated) {
            
            // Grow size
            if (unique_count >= unique_size - 1) {
                unique_size *= 2;
                uniques = realloc(uniques, sizeof(char*) * unique_size);
            }

            uniques[unique_count] = malloc(sizeof(char) * strlen(strs[i]) + 1);
            strcpy(uniques[unique_count++], strs[i]);
        }
    }

    if (unique_count == 0) {
        free(uniques);
        return NULL;
    }

    uniques[unique_count] = NULL;

    return uniques;
}

/* Free a pointer array of words */
void freeWords(char** ptr) {
    for (register int i = 0; ; i++) {
        if (ptr[i] == NULL)
            break;
        free(ptr[i]);
    }
    free(ptr);
}

int main(void){
    char* questions[5];
    questions[0] = "What are local variables?";
    questions[1] = "What is an identifier?";
    questions[2] = "What is recursion?";
    questions[3] = "What is a pointer?";
    questions[4] = "What is the purpose of applying static to a local array?";

    char** q1 = tokenize(questions[0]);
    char** q2 = tokenize(questions[1]);
    char** q3 = tokenize(questions[2]);
    char** q4 = tokenize(questions[3]);
    char** q5 = tokenize(questions[4]);

    char* insAnswers[5];
    //cannot use strcpy() even though the TAs are allowed to...

    // the next 10 lines of code do exactly what malloc and strcpy does
    char ins1[112] = "Variables defined in function definition are local variables. They can be accessed only in that function scope.";
    char ins2[78] = "Identifiers are user defined names given to variables, functions and arrays.";
    char ins3[232] = "A function calling itself again and again to compute a value is referred to as recursive function or recursion. Recursion is useful for branching processes and is effective where terms are generated successively to compute a value.";
    char ins4[89] = "A pointer is a variable that stores the memory address of another variable as its value.";
    char ins5[213] = "By making a local array definition static the array is not created and initialized every time the function is called and it is not destroyed every time the function is exited. Also, the execution time is reduced.";
    insAnswers[0] = ins1;
    insAnswers[1] = ins2;
    insAnswers[2] = ins3;
    insAnswers[3] = ins4;
    insAnswers[4]=  ins5;

    char** insAns1 = tokenize(insAnswers[0]);
    char** insAns2 = tokenize(insAnswers[1]);
    char** insAns3 = tokenize(insAnswers[2]);
    char** insAns4 = tokenize(insAnswers[3]);
    char** insAns5 = tokenize(insAnswers[4]);

    char** stuAnswers = getStudentAnswers(questions,5,500);

    int result;
    int** vec = ans2Vectors(insAnswers[0],stuAnswers[0]);
    for(int i = 0; i < 2;i++){
        printf("\n");
        for(int j = 0; j < 14; j++){
            printf("%d",vec[i][j]);
        }
    }

    
    printf("%f\n",cosineSimilarity(insAnswers[3],stuAnswers[3]));
    
    
    // char** uniqueWords =  malloc(200); // make a double pointer to hold the vectors
    // uniqueWords = getUniqueWords(tokenize(insAnswers[0])); // NEXT MAKE A LOOP TO PRINT the vectors within the array
  

    // printf("%d",vector[0]);


    // printf("%f\n",cosineSimilarity(insAnswers[0],stuAnswers[0]));

    return 0;
};