def get_fre(c,his):
  return his.get(c)

def add_to_hist(c,his):
  if c in his:
    n = his.get(c)+1
  else:
      n = 1
  his.update({c:n})

def make_hist(str):
  diction = {}
  for c in str:
    add_to_hist(c,diction)
  return diction

def histoprint(d):
  for e in d:
    print(e,": ",'#'*d.get(e))

def file_to_hist(fname):
  dict = {}
  f = open(fname,"r")
  content = f.read()
  dict = make_hist(content)
  f.close()
  return dict

if __name__ == '__main__':



## 1C: Yes it is, since the output exactly follow the rules of the input, which is a pure function
## return l1 - l2
    def remove_items(l1,l2):
        list1 = l1
        list2 = l2
        for i in list1:
            i = i.upper()
        for j in list2:
            j = j.upper()
        for e in list2:
            temp = []
            if e  in list1:
                list1.remove(e)
        return list1

    def filter_punctuation(s1,s2):
        result = ""
        for c in s1:
            if c not in s2:
                result = result + c
        return  result
    ll1 = ["a","The" "b"]
    ll2 = [ "The" "b"]
    print((remove_items(ll1,ll2)))
