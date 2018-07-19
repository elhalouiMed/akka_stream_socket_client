This is a simple example of akka socket client that use akka stream to send Bytes from a given data file to server
Dataset: http://stat-computing.org/dataexpo/2009/2008.csv.bz2


to run this example you need to have socket server running somewhere and configure host an port in

   val serverConnection = Tcp().outgoingConnection("localhost", 9182)


this example is developped with this repository as a client for demonstration

https://github.com/elhalouiMed/scala_akka_persistence_postgresql

enjoy !
