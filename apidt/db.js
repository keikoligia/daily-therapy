/*async function connect(){
  if(global.connection && global.connection.state !== 'disconnected')
      return global.connection;

  const mysql = require("mysql2/promise");
  const connection = await mysql.createConnection("mysql://root:1234@localhost:3306/dailytherapy");
  //mysql://usuario:senha@servidor:porta/banco
  console.log("CONECTOU NO MYSQL");
  global.connection = connection;
  return connection;
}

connect();

async function selectUsuario(){
  const conn = await connect();
  const [rows] = await conn.query('SELECT * FROM usuario');
  return rows;
}

async function insertUsuario(usuario){
  const conn = await connect();
  const sql = 'INSERT INTO usuario(nome,email,senha) VALUES (?,?,?);';
  const values = [usuario.nome, usuario.email, usuario.senha];
  return await conn.query(sql, values);
}

async function updateUsuario(id, usuario){
  const conn = await connect();
  const sql = 'UPDATE usuario SET nome=?, email=?, senha=? WHERE id=?';
  const values = [usuario.nome, usuario.email, usuario.senha, id];
  return await conn.query(sql, values);
}

async function deleteUsuario(id){
  const conn = await connect();
  const sql = 'DELETE FROM usuario where id=?;';
  return await conn.query(sql, [id]);
}

module.exports = {selectUsuario, insertUsuario, updateUsuario, deleteUsuario}
*/