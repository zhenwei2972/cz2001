from __future__ import print_function

from networkx.classes import graph
from Nodemap import *
import heapq
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
    lines_skip_first = file.readlines()[0:]
    Hospitals =[]
    for line in lines_skip_first:
        Hospitals.append(int(line.rstrip()))
    print("hospital list",Hospitals) 
    return Hospitals
    file.close()

#not in use
#def DFS(G, origin, destination):
 #   #create empty path list to store our DFS search path thus far
  #  path_list = [[origin]]
#
    #while path list is not empty
 #   while path_list:
        #pop last path out from the path list to explore
   #     path = path_list.pop()
        #if last node in this path is the destination value/node, correct path is found
    #    last_node = path[-1] #slice path list -1 to get last element in path list
     #   if last_node ==destination:
      #      return path
        
        #else if not, continue DFS by adding new paths.
        #we do this by adding the neighbours of the last node which are not already on the list. 
       # else: 
        #    for node in G[last_node]:
         #       if node not in path:
                    # add a new path, where first element is path and second element is neighbouring node
          #          new_path = path + [node]
                    # add the new path to the total path list / tree
           #         path_list.append(new_path)
   # print("no path found")
    
def BFS(G,origin,destination):
    #handle edge case where origin = destination, that is, origin = hospital
    if origin == destination:
        print("origin same as destination, skipped")
        return [[origin]]
    path_list = [[origin]]
    while path_list:
        path = path_list.pop(0)
        last_node = path[-1]
        if last_node in destination: #if node is a hospital, return
            return path
        else:                        #else, add path + current node to list of paths
            for node in G[last_node]:
                if node not in path:
                    new_path = path +[node]
                    path_list.append(new_path)
    print("no path found")

def BFS_Top(G,origin,destination,k):
    incrementer =1
    while(incrementer<=k):
        path = BFS(G,origin,destination) 
        if (path != None): #if there is a returned path to nearest hospital
            print("Top Hospital",incrementer,"is",path[-1]) #find top hospital
        else:
            print("There is no nearest hospital")
        incrementer+=1
        destination.remove(path[-1]) #Remove top hospital and rerun

#def djikstraSingleSource(G,origin,destination):
     # Single source, find shortest path from one vertex
    # in a graph to another vertex. 
    # answer set is from one source vertex to every other vertex hence |V|
    # where shortest is defined by number of edges ( unweighted )/ ( all weight 1)
    # or total weight (djikstra)
    # graph must be a weighted, directed graph. 
    # S-> solution set of solved vertices , shortest path that have been determiend.
    # V-S -> remaining vertices
    # pick an edge in V-S set , the connected edge is added to solution set. 
    # try to iteriate through all edges/nodes in V-S based on shortest path
    # already formed in S. 
    # d , size of number of v , |V| it is an array of estimates for length of shortest path
    # as more data is added, this estimate in array d is refined. 
    # d -> distance from source node to all vertices ( array )
    # pi -> size |v| ( parent index ) store the parent index of each vertex.
    # pi will store entire tree/ path. will contain every vertex
    # pi s v x x u x
    #   -1 u     s
    # then can build the pathstarting from V, whose parent is u, and u whose parent is S
    # hence the path is S-> U -> V
    # we use the PriorityQueue to store a key value for every vertex. where keyvalue is d
    # recap
    # Basic steps
    # 1) Initialize d and pi
    # 2) Set S to empty
    # 3) While there are vertices in V-S (consider all edges in remaining set)
    #    i) Do a greedy pick, with respect to shortest path estimate stored in d
        # pick smallest d value, move this vertex from V-S to S.
        # ii) for all remaining vertexes in candidate set that are directly connected to u
        # check if they can be updated or not. 
def dijsktra(graph, initial, end):
    # shortest paths is a dict of nodes
    # whose value is a tuple of (previous node, weight)
    shortest_paths = {initial: (None, 0)}
    current_node = initial
    visited = set()
    
    while current_node != end:
        visited.add(current_node)
        destinations = graph[current_node]
        weight_to_current_node = shortest_paths[current_node][1]

        for next_node in destinations:
            weight = 1 + weight_to_current_node
            if next_node not in shortest_paths:
                shortest_paths[next_node] = (current_node, weight)
            else:
                current_shortest_weight = shortest_paths[next_node][1]
                if current_shortest_weight > weight:
                    shortest_paths[next_node] = (current_node, weight)
        
        next_destinations = {node: shortest_paths[node] for node in shortest_paths if node not in visited}
        if not next_destinations:
            return "Route Not Possible"
        # next node is the destination with the lowest weight
        current_node = min(next_destinations, key=lambda k: next_destinations[k][1])
    
    # Work back through destinations in shortest path
    path = []
    while current_node is not None:
        path.append(current_node)
        next_node = shortest_paths[current_node][0]
        current_node = next_node
    # Reverse path
    path = path[::-1]
    return path        


    # S = null , V-S = {all vertex}
    # d = |V| where all are inf
    # pi = |V|
    # S U V X Y
    #pi   0 inf inf inf inf 
    #d    ^ ^ ^ ^
    # delete S from V-S since S is the smallest distance. (d )
    # add S to Solution Set s
    # neightbours of S is u and x
    # distance of x is 5, distance of u is 10 ( from S ) hence update the current shortest path
    # also remember to update the parent, since new path is found. 
    # S U V X Y
    #d0 10 inf 5 inf 
    #pi ^ S ^ S ^
    # pick next one with smallest distance from s so far. which is x
    # include x into s solution set
    # check if existing vertex can be refined or not .
    # since neighbours of x is u,v,y
    # distance must be distance from source to X , + the weight to neighbours.
    # hence since S X Y, so 5+2 =7
    # also S X U is 5 +3 so 8.
    # update distance estimate and update parent node in pi array as well.
    # where pi[u] =x
    # only check neighburs which are not finalized in solution set s
    # 
    # psuedocode of djikstra
    # for each vertex v {
    # d[v] = infinity;
    # pi[v] = null;
    # S[v] =0;
    # }
    # set distance of source to 0 
    # d[source] =0;
    # put vertices in priority queue Q , where priority is defined by d array, increasing order
    # while Q.notEmpty():
    # extract vertex with smallest d value to solution set
    # u = extractCheapest(Q)
    # S[u] =1 //swtich S value to 1, marking it as part of solution
    # for each neighbour of u,
    # check if distance esimate can be updated or not
    # check if distance of neighbour is not yet finalized
    # check if new distance with u as its potential parent 
    # if new distance is smaller, we found ashorter path
    # hence update
    # remove neighbour v from queue.
    # update the d[v] and pi[v]
    # then insert v into queue. 
    # else do nothing
    # end when queue is empty.

    # worst complexity is big O(|V|^2) generally.
    # most of it comes from the while loop. cos 
    # every single vertex is extracted from the queue 
    # and processing is done. 
    # so cost of extraction is |V|. CostExtract
    # for every single edge, it is accessed at least once 
    # |E| * CostUpdate
    # if implemented in matrix, cost of update is constant time
    # because array d, pi, acces time is O(1)
    # if implemented with queue then is V^2
    # else if use heap, fix heap can drop to log v
    # |v| + |e| * log(v) 
    #Runing Djikstra for every node.
    
def BFSDisplay():
    #nparray = ReadFile()
    networkmap = GenerateNetworkMap()
    #print(networkmap) What is this? nothing changes if we comment out ##########################################################
    start = getStart()
    end =[]
    end = Hospitals
    
    #fix edge case , crash when out of range 
    combinetext = []
    for node in range(1,getNumNodes()): #loop for every node
        if(node in end): #if node is a hospital, skip
            print(node,"skipped , is a hospital \n")
            continue
        else:
            start = node
            path = BFS(networkmap,start, end)
            print(start,end)
            if(path!=None): #if path is not empty, print details
                print("Nearest Hospital is:",path[-1])
                print("Shortest distance:",len(path), "\nShortest path is:",path, "\n")
                combinetext.extend(["start node: ", node,"Distance to nearest hospital: ",len(path), "Shortest path: ", path, "Nearest hospital: ", path[-1], "\n"])
                np.savetxt("BFSoutput.txt", combinetext,delimiter=" ", fmt="%s")
            # edges = ConvertNodeToEdge(path)
            # PrintGraph(networkmap, node)
            else: #if path empty, skip because it means node is isolated
                print("skipped, source node not connected to graph \n")
                
def BFSTopDisplay(k):
    #nparray = ReadFile()
    networkmap = GenerateNetworkMap()
    #print(networkmap) What is this? nothing changes if we comment out ##########################################################
    start = getStart()
    end =[]
    end = Hospitals
    
    #fix edge case , crash when out of range 
    combinetext = []
    print(getNumNodes())
    for node in range(getNumNodes()): #loop for every node
        DupHospital= end.copy()
        #DupHospital = Hospitals
        for i in range(1,k+1):
            if(node in Hospitals): #if node is a hospital, skip
                if (i == 1):
                    print(node,"skipped , is a hospital \n") #print once only
                continue
            else:
                start = node
                path = BFS(networkmap,start, DupHospital)
                print(start+1,end, DupHospital)
                if(path!=None): #if path is not empty, print details
                    print("Nearest Hospital", i, "is:",path[-1])
                    print("Shortest distance:",len(path), "\nShortest path is:",path, "\n")
                    combinetext.extend(["start node: ", node,"Distance to nearest hospital: ",len(path), "Shortest path: ", path, "Nearest hospital: ", path[-1], "\n"])
                    np.savetxt("BFSoutput.txt", combinetext,delimiter=" ", fmt="%s")
                    DupHospital.remove(path[-1])
                # edges = ConvertNodeToEdge(path)
                # PrintGraph(networkmap, node)
                else: #if path empty, skip because it means node is isolated
                    print("skipped, source node not connected to graph \n")
                    break
                
            
    
if __name__=="__main__":
    #push in nparray for bigfile.

    #
    #1A 1B running BFS for every node.
    #
    #nparray = ReadFile()
    #generate random 
   # BFSDisplay()
    
    Hospitals =ReadHospitalFile("Hospital")
    networkmap = GenerateNetworkMap()
    #print(networkmap) What is this? nothing changes if we comment out ########################################
    start = getStart()
    end =[]
    #prototype , probably not correct way to do it. 
    ######NOTE: every generated node map is different for (a)&(b) / (c) / (d)!!
    print("Part (a) & (b)")
    BFSTopDisplay(1)
    print("Part (c)")
    BFSTopDisplay(2)
    print("Part (d)")
    k = int(input("Enter value of k for k nearest hospitals:"))
    BFSTopDisplay(k)
    
    '''

    combinetext = []
    for y in range(getNumNodes()):
        if(y in end):
            print(y,"skipping , is a hospital")
            #if hospital skip
            continue
        else:
            start =y
            path = dijsktra(networkmap,start, end)
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
                edges = ConvertNodeToEdge(path)
                PrintGraph(networkmap, y)
            else:
                print("skipped, source node not connected to graph")
                '''



    #blue is start. 
    #red is end 
   # PrintGraphSimple(networkmap, edges)