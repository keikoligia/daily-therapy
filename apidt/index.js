var express = require('express');
var mysql = require('mysql2');
var bodyParser = require('body-parser');

//conexao c/ mysql
var conex = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '1234',
  database: 'dailytherapy'
})

var app = express();
var publicDir = (__dirname+'/public');
app.use(express.static(publicDir));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

//selecionar todos os users 
app.get("/usuarios", (req, res, next) => {
  console.log("chegou 1");
  conex.query("SELECT * FROM USUARIO"),  function (error, result, fields){
    conex.on('error', function(err){
      console.log("DEU ERRO NO MYSQL", err);
    });
      console.log("chegou 2");
      return res.json(JSON.stringify(result))
  }
});

//selecionar user pelo nome
app.post("/usuario/login", (req, res, next) => {
  var nomeUsuario = req.body;
  console.log(nomeUsuario);
  var query = "SELECT * FROM usuario WHERE nome =" + "'" + nomeUsuario.nome  + "'";
  console.log(query);

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("DEU ERRO NO MYSQL", err);
    });
    if(result && result.length)
    {
      return res.status(200).json(result[0]);
    }
    else
    {
      return res.status(404).json({error: 'Usuario nao encontrado'});

    }
  });
});

app.post("/usuario", (req, res, next) => {
  console.log("Chegou")
  var user = req.body; // pega as informacoes da requisicao
  var query = "INSERT INTO USUARIO (nome, contato, nomeContato, email, senha) VALUES('" + user.nome + "','" + user.contato + "','" + user.nomeContato + "','" + user.email + "','" + user.senha + "')";

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("ERRO NO MYSQL", err);
    });
    console.log(result);
    conex.query("SELECT * FROM USUARIO WHERE idUsuario= " + result.insertId, function(e, r, f){
      console.log(r);
      return res.status(200).json(r[0]);
    })
  });
});

app.delete("/usuario/delete/:id", (req, res, next) => {
  var query = "DELETE FROM USUARIO WHERE idUsuario = " + req.params.id;

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("ERRO NO MYSQL", err);
    });
    return res.json(JSON.stringify(result));
  });
});

app.post("/usuario/remedio/:id", (req, res, next) => {
  var query = "SELECT * FROM remedio WHERE nome LIKE '%" + req.params.id + "%'";

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("DEU ERRO NO MYSQL", err);
    });
    if(result && result.length)
      res.end(JSON.stringify(result));
    else
      res.end(JSON.stringify('Nenhum remedio p/ resgatar'));
  });
});

app.put("/usuario/remedio/:id", (req, res, next) => {
  var remedio = req.body; // pega as informacoes da requisicao
  var query = "INSERT INTO REMEDIO (nomeRemedio, horario) VALUES('" + remedio.nomeRemedio + "','" + remedio.horario + "')";

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("ERRO NO MYSQL", err);
    });
    if(result && result.length)
      res.end(JSON.stringify(result));
    else
      res.end(JSON.stringify('Erro ao inserir remedio'));
  });
});

app.delete("/usuario/remedio/delete/:id", (req, res, next) => {
  var query = "DELETE FROM REMEDIO WHERE idRemedio = " + req.params.id;

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("ERRO NO MYSQL", err);
    });
    if(result && result.length)
      res.end(JSON.stringify(result));
    else
      res.end(JSON.stringify('Erro ao deletar remedio'));
  });
});

/*
app.put('/put/:id', (req, res) => {
  const id = req.params.id;
  const item = data.find(item => item.id == id); 
               
  item.nome = req.body.nome;
  item.raca = req.body.raca;
  item.image = req.body.image;        
               
  return res.json({data});

  app.delete('/api/dog/delete/:id', (req, res) => {
  const id = req.params.id;
  const index = data.findIndex(item => item.id == id);
  data.splice(index,1)
  console.log(data)
  
  return res.json({data})
})
});*/

app.listen(3000, () => {
  console.log("APP RODANDO NA PORTA 3000");
})