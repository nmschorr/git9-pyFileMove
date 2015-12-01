'''
Created on Nov 22, 2015

@author: user
'''
#print glob.glob("c:/*.*")

def list_files(path):
    # returns a list of names (with extension, without full path) of all files 
    # in folder path
    files = []
    for name in os.listdir(path):
       # print (name)
        if os.path.isfile(os.path.join(path, name)):
            files.append(name)
           # print (name)
    return files 
