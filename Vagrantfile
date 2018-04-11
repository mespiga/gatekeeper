# -*- mode: ruby -*-
# vi: set ft=ruby :

# Make sure that vagrant-cachier is available. If not, simply exit.
unless Vagrant.has_plugin?('vagrant-cachier')
  raise 'The vagrant-cachier plugin is not available. Please make sure you have installed it for your platform.'
end


Vagrant.configure(2) do |config|
  # Store the host operating system name.
  host                  = RbConfig::CONFIG['host_os']

  config.vm.box           = 'ubuntu/trusty64'
  config.vm.box_url 	    = 'https://cloud-images.ubuntu.com/vagrant/trusty/current/trusty-server-cloudimg-amd64-vagrant-disk1.box'
  config.vm.hostname      = 'localhost'
  config.vm.network       :forwarded_port, guest: 8088,   host: 9010
  config.vm.network       :forwarded_port, guest: 80,   host: 9001

#  if run_in_windows
#    authorize_key_for_root config, true, 'C:/Users/xpto/Desktop/chaves/id_rsa.pub'
#    authorize_key_for_root config, false, 'C:/Users/xpto/Desktop/chaves/id_rsa.pub'
#  end

 # Set up SSH options. This allows to forward the local SSH keys to the instance, so git can use them.
  config.ssh.forward_agent      = true
  #config.ssh.insert_key        = false

  # IP address configuration.
  config.vm.network       :private_network, ip: '69.69.69.69'
  # Shares.
  config.vm.synced_folder   '.',  '/vagrant', mount_options: ["dmode=775,fmode=664"]

  # Configure vagrant-cachier plugin, which allows the vagrant host to cache
  # anything that is downloaded to the guest /var/cache. Very handy while provisionning
  # VMs often. Please note that the cache folder should be located
  if Vagrant.has_plugin?('vagrant-cachier')
    config.cache.scope  = :box
  end

  # Make this box run with VirtualBox.
  config.vm.provider  :virtualbox do |vm|
    vm.gui                    = false
    vm.check_guest_additions  = true
    vm.cpus                   = 2
    vm.memory                 = 2048
  end

  config.vm.provision :shell, inline: <<-SHELL
    echo "Installing JVM Oracle jdk 8........."
    #sudo apt-get install default-jdk
    sudo apt-get install oracle-java8-installer
    echo "Finish installing JVM oracle-java8."

    echo "Installing Maven........."
    sudo apt-get install maven
    echo "Finish installing Maven.
    
    echo "Installing postgres......"
    sudo apt-get install postgresql postgresql-contrib
    echo "Finish installing postgres/""

    echo "Installing mongoDB........."
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
    echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
    sudo apt-get update
    sudo apt-get install -y mongodb-org
    echo "Finish installing mongoDB."
    echo "Starting mongoDB........."
    sudo service mongod start
    echo "Started mongoDB."
    echo "Testing mongoDB........."
    grep "waiting for connections on port 27017" /etc/mongod.conf
    echo "Finish testing mongoDB."
    sudo su -c "gem install sass"

    echo "Installing PIP package installer for python......"
    sudo apt-get install python-pip python-dev build-essential
    sudo pip install --upgrade pip
    sudo pip install --upgrade virtualenv

    echo "Installing excell lib for python......"
    sudo pip install openpyxl

  SHELL
end
