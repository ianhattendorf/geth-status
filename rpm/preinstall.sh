#!/bin/sh

set -ue

/usr/bin/getent group geth-status > /dev/null || /usr/sbin/groupadd -r geth-status
/usr/bin/getent passwd geth-status > /dev/null || /usr/sbin/useradd -r -d /opt/geth-status -s /sbin/nologin -g geth-status geth-status
