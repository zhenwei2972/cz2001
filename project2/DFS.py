from __future__ import print_function
from Nodemap import *
from heapq import heappush, heappop
from itertools import count
import networkx as nx
from ReadFile import *

def ReadFile():
    edge = np.loadtxt("roadNet-PA.txt", dtype="int32")
    array = np.array(edge)
    return array
def ReadHospitalFile(fileName):
    file = open('%s.txt'% fileName) 
    firstline= file.readline().strip()
    numberOfHospitals = firstline[2:]
    #print("firstline",firstline)
    lines_skip_first = file.readlines()[0:]
    Hospitals =[]
    for line in lines_skip_first:
        Hospitals.append(int(line.rstrip()))
    print("hospital list",Hospitals)
    return Hospitals
    file.close()
#not in use
def DFS(G, origin, destination):
    #create empty path list to store our DFS search path thus far
    path_list = [[origin]]

    #while path list is not empty
    while path_list:
        #pop last path out from the path list to explore
        path = path_list.pop()
        #if last node in this path is the destination value/node, correct path is found
        last_node = path[-1] #slice path list -1 to get last element in path list
        if last_node ==destination:
            return path
        
        #else if not, continue DFS by adding new paths.
        #we do this by adding the neighbours of the last node which are not already on the list. 
        else: 
            for node in G[last_node]:
                if node not in path:
                    # add a new path, where first element is path and second element is neighbouring node
                    new_path = path + [node]
                    # add the new path to the total path list / tree
                    path_list.append(new_path)
    print("no path found")
    
def BFS(G,origin,destination):
    #handle edge case where origin = destination
    if origin == destination:
        print("origin same as destination, skipped")
        return [[origin]]
    path_list = [[origin]]
    while path_list:
        path = path_list.pop(0)
        last_node = path[-1]
        if last_node in destination:
            return path
        else:
            for node in G[last_node]:
                if node not in path:
                    new_path = path +[node]
                    path_list.append(new_path)
    print("no path found")

if __name__=="__main__":
   # nparray = ReadFile()
    #generate random 
    Hospitals =ReadHospitalFile("Hospital")
    networkmap = GenerateNetworkMap()
    print(networkmap)
    start = getStart()
    end =[]
    #TODO read input file, to deteremine how many hospital
    #for x in range(0,5,1):
        #append only hospitals to end list 
       # end.append(getHospital())
    end = Hospitals
    
    #fix edge case , crash when out of range 
    combinetext = []
    for y in range(getNumNodes()):
        if(y in end):
            print(y,"skipping , is a hospital")
            #if hospital skip
            continue
        else:
            start =y
            path = BFS(networkmap,start, end)
            print(start,end)
            if(path!=None):
                print("Nearest Hospital is",path[-1])
                print("Shortest path is ",path)
                combinetext.append("start node")
                combinetext.append(y)
                combinetext.append(path)
                combinetext.append("hospital")
                combinetext.append(path[-1])
                combinetext.append("\n")
                np.savetxt("BFSoutput.txt", combinetext,delimiter=" ", fmt="%s")
            #trying to import big file. cannot work. too slow. 
            #networkmap = fasterRead()
            #path = DFS(networkmap,273,3861)
                edges = ConvertNodeToEdge(path)
            #edges = nx.shortest_path(networkmap, getStart(), getHospital())
        ##    path2 = nx.bfs_tree(networkmap,start)
        ##  print("Correct path is ",path2)
                PrintGraph(networkmap, y)
            else:
                print("skipped, source node not connected to graph")
    


    #blue is start. 
    #red is end 
   # PrintGraphSimple(networkmap, edges)