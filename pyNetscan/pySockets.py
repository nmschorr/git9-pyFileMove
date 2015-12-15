'''
Created on Dec 11, 2015
@author: Nancy Schorr
'''
if __name__ == '__main__': pass
import socket
import netaddr
import nmap
import sys

wdtvlive = '192.168.1.68'     ## port 80, 139, 443 open
uraniaAddy = '192.168.1.76'
network = "192.168.1.250/24"
deliaAddy = '192.168.1.69'


########################### findHosts()#####################
def  findHosts(innerNetwork):
         
        # from http://subneter.com/network-scan-python-nmap/
        # find all hosts in network-return host list in str format
        # @variable network: The network
        # @return   hostList: All hosts in the network
      
        ipNet = netaddr.IPNetwork(innerNetwork)
        hosts = list(ipNet)
 
        #Removing the net and broad address if prefix is under 31 bits
        if len(hosts) > 2:
            hosts.remove(ipNet.broadcast)
            #hosts.remove(ipNet.innerNetwork)
 
        hostList = [str(host) for host in hosts]
        print "Checking these available host ip's: " + str(hostList)
        print ""
        myrange= range(65, 155)
        lenList = len(hostList) 
        
        
        ##  for i in range( lenList  ):
        for i in hostList[ hostList.index('192.168.1.55'):] :
            print "val of i: " + i
            checkUp(i)
            

############portCheck()####################################

def portCheck(remoteServerIP) :
    hostName = socket.gethostbyaddr(remoteServerIP)
    print "domain name is: " + str(socket.getfqdn(remoteServerIP))
    
    print "hostname is %s" % str(hostName[0])
    for tport in range(1,1025):  
        # print "working on port: " + str(tport)
        msock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        msock.settimeout(2)
        result = msock.connect_ex((remoteServerIP, tport))
        sys.stdout.write(str(tport)+" ")
        #print "result of sock connect on port {0} - {1}".format(str(tport),str(result))
        if result == 0 :
            print ''
            print "Port {}: \t Open".format(tport)
        msock.close()

########################### socketStylen()#####################
def socketStylen():
    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serversocket.bind(('localhost', 8089))
    serversocket.listen(5) # become a server socket, maximum 5 connections
    
    print "Starting pSockets"
    print "serversocket looks like this" + str(serversocket)
    
    while True:
        connection, address = serversocket.accept()
        buf = connection.recv(64)
        print "Waiting for connection"
        if len(buf) > 0:
            print "Connection received. Something connected on serversocket"
            print "value of connection: " + str(connection)
            print "value of address: " + str(address)
            print buf
            break
########################### checkUp()#####################

def checkUp(hostAddy):
    myout = 0
    mysock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        mysock.settimeout(4)
        mysock.connect((hostAddy, 135)) ## was 22
        hostName = socket.gethostbyaddr(hostAddy)
        print "Port 135 reachable on: " + str(hostAddy) + " " + str(hostName)
       
    except socket.error as err:
        aaa = str(err)
        #print "aaa : " + aaa
        if "timed out" in aaa :
            #print "timed out!!!"
            myout = 1
        
        if myout == 0 :   
            print "connect string: %s" % err
    mysock.close()
########################### end checkUp #####################


#################checkPort()###############################

def checkPort(hostAddy):
    myout = 0
    mysock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        mysock.settimeout(1)
        mysock.connect((hostAddy, 135)) ##22
        print "Port 22 reachable on %s" % hostAddy
        
    except socket.error as err:
        aaa = str(err)
        #print "aaa : " + aaa
        if "timed out" in aaa :
            #print "timed out!!!"
            myout = 1
        
        if myout == 0 :   
            print "connect string: %s" % err
    mysock.close()




######################## tryNM()##########################
#import nmap
def tryNM(myIP) :
    #myIP = '192.168.1.68'
    hostName = socket.gethostbyaddr(myIP)    
    print "------------------ Starting -------------"
    print "hostname is %s" % str(hostName[0])
    nm = nmap.PortScanner()
    print "going to print live ports on ip: " + myIP
    
    mylist =  nm.scan(myIP, '22-443') 
    for i in mylist :
        print mylist[i]

    print "going to print scaninfo: "
    print str(nm.scaninfo())
    
    print "going to print all_hosts: "
    print str(nm.all_hosts())
        
    print "going to print state: "
    print str(nm[myIP].state())
    
    print "going to print all_protocols: "
    print str(nm[myIP].all_protocols()  )
    
    print "going to print tcp-keys: "
    print str(nm[myIP]['tcp'].keys() )
    
    print "going to print has_tcp22: "
    print str(nm[myIP].has_tcp(22) )
    
    print "going to print has_tcp23: "
    print str(nm[myIP].has_tcp(23) )
        
    for host in nm.all_hosts():
        print('Host : %s (%s)' % (host, nm[host].hostname()))
        print('State : %s' % nm[host].state())
        for proto in nm[host].all_protocols():
            print('Protocol : %s' % proto)
    
            lport = nm[host][proto].keys()
            lport.sort()
            #for pport in lport:
                #''
                #print ('port : %s state : %s' % (pport, nm[host][proto][pport]['state']) )
    print "all done with network map"
    
############# execute section ######################################
## uncomment functions in this section to run them

print "Going to show info about hosts alive in local network."

# hosts = findHosts(network)
##checkUp(uraniaAddy)
#portCheck(wdtvlive)
tryNM(wdtvlive)
tryNM(deliaAddy)

