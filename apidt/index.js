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
  conex.query("SELECT * FROM USUARIO"),  function (error, result, fields){
    conex.on('error', function(err){
      console.log("DEU ERRO NO MYSQL", err);
    });
    if(result && result.length)
      res.end(JSON.stringify(result));
    else
      res.end(JSON.stringify('Nenhum usuario p/ resgatar'));
  }
});

//selecionar user pelo nome
app.post("/usuario:/nome", (req, res, next) => {
  var postData = req.body; // pega as informacoes da requisicao
  var nomeUsuario = postData.usuario;
  var query = "SELECT * FROM USUARIO WHERE nome LIKE '%" + nomeUsuario + "%'";

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("DEU ERRO NO MYSQL", err);
    });
    if(result && result.length)
      res.end(JSON.stringify(result));
    else
      res.end(JSON.stringify('Nenhum usuario p/ resgatar'));
  });
});

app.put("/usuario/:id", (req, res, next) => {
  var user = req.body; // pega as informacoes da requisicao
  var query = "INSERT INTO USUARIO (nome, email, senha) VALUES('" + user.nome + "','" + user.email + "','" + user.senha + "')";

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("ERRO NO MYSQL", err);
    });
    if(result && result.length)
      res.end(JSON.stringify(result));
    else
      res.end(JSON.stringify('Nenhum usuario p/ inserir'));
  });
});

app.delete("/usuario/delete/:id", (req, res, next) => {
  var query = "DELETE FROM USUARIO WHERE idUsuario = " + req.params.id;

  conex.query(query, function (error, result, fields){
    conex.on('error', function(err){
      console.log("ERRO NO MYSQL", err);
    });
    if(result && result.length)
      res.end(JSON.stringify(result));
    else
      res.end(JSON.stringify('Nenhum usuario p/ deletar'));
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
});*/

app.delete('/api/dog/delete/:id', (req, res) => {
  const id = req.params.id;
  const index = data.findIndex(item => item.id == id);
  data.splice(index,1)
  console.log(data)
  
  return res.json({data})
})

app.listen(3000, () => {
  console.log("APP RODANDO NA PORTA 3000");
})