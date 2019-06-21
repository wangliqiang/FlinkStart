const db = require("./db.js");

const mysql = require("mysql");
const express = require("express");

const app = express();
var server = require('http').createServer(app);
var io = require("socket.io")(server);

var conn = mysql.createConnection(db);

conn.connect();

io.on("connection", function (socket) {
  console.log('connected');
  socket.on('disconnect', function () {
    console.log('disconnect')
  });
  // io.on("message", function (obj) {
  let sql = "select * from topn order by id desc limit 1";
  let resultStr, resultJson;
  conn.query(sql, function (err, result) {
    if (err) {
      console.log(err);
    }
    if (result) {
      resultJson = result;
      resultStr = resultJson;
      io.emit("message", resultJson);
    }
  });

  setInterval(function () {
    conn.query(sql, function (err, result) {
      if (err) {
        console.log(err)
      }
      if (result) {
        resultJson = result;
        if (resultStr[0].id !== resultJson[0].id) {
          resultStr = resultJson;
          io.emit("message", resultStr);
        }
      }
    });
  }, 3000);
  // });
})

server.listen(3000, "127.0.0.1");
console.log('success listen at port:3000......');