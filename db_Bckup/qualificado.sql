-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 29-Maio-2022 às 15:51
-- Versão do servidor: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qualificado`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `certificacao`
--

CREATE TABLE `certificacao` (
  `codigo` bigint(20) NOT NULL,
  `nome_certificacao` varchar(255) NOT NULL,
  `usuario_codigo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `certificacao`
--

INSERT INTO `certificacao` (`codigo`, `nome_certificacao`, `usuario_codigo`) VALUES
(2, 'Informática', 3),
(3, 'Análises Clínicas ', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `experiencia_profissional`
--

CREATE TABLE `experiencia_profissional` (
  `codigo` bigint(20) NOT NULL,
  `area_de_atuacao` varchar(255) NOT NULL,
  `funcao` varchar(255) NOT NULL,
  `local_trabalho` varchar(255) NOT NULL,
  `titulo_academico` varchar(255) NOT NULL,
  `usuario_codigo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `experiencia_profissional`
--

INSERT INTO `experiencia_profissional` (`codigo`, `area_de_atuacao`, `funcao`, `local_trabalho`, `titulo_academico`, `usuario_codigo`) VALUES
(3, 'Enfermagem', 'Enfermeira', 'Hospital Central do Lubango', 'Licenciado', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `formacao_academica`
--

CREATE TABLE `formacao_academica` (
  `codigo` bigint(20) NOT NULL,
  `estado` bit(1) NOT NULL,
  `nome` longtext NOT NULL,
  `usuario_codigo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `formacao_academica`
--

INSERT INTO `formacao_academica` (`codigo`, `estado`, `nome`, `usuario_codigo`) VALUES
(2, b'1', 'Formado Em Engenharia Informática pelo Instituto Superior Politécnico Independente (I.S.P.I)', 1),
(4, b'1', '2020-Formads pelo Instituto Superior Politécnico Tundavala na especialidade de Enfermagem', 3),
(5, b'1', '2014- Técnica Média de Enfermagem pelo Instituto Médio de Saúde do Lubango Ana Paula', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `grupo`
--

CREATE TABLE `grupo` (
  `codigo` bigint(20) NOT NULL,
  `estado` bit(1) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `grupo`
--

INSERT INTO `grupo` (`codigo`, `estado`, `nome`) VALUES
(1, b'1', 'Usuario'),
(2, b'1', 'Administrador'),
(3, b'1', 'Empregador');

-- --------------------------------------------------------

--
-- Estrutura da tabela `grupo_permissao`
--

CREATE TABLE `grupo_permissao` (
  `codigo_grupo` bigint(20) NOT NULL,
  `codigo_permissao` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `grupo_permissao`
--

INSERT INTO `grupo_permissao` (`codigo_grupo`, `codigo_permissao`) VALUES
(2, 1),
(3, 3),
(3, 4),
(1, 2),
(1, 4);

-- --------------------------------------------------------

--
-- Estrutura da tabela `permissao`
--

CREATE TABLE `permissao` (
  `codigo` bigint(20) NOT NULL,
  `nome` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `permissao`
--

INSERT INTO `permissao` (`codigo`, `nome`) VALUES
(1, 'CADASTRAR_USUARIOS'),
(2, 'PERFIL_PROFISSIONAL'),
(3, 'RECRUTAR'),
(4, 'CONTACTAR_ADMIN');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `codigo` bigint(20) NOT NULL,
  `bairro` varchar(255) DEFAULT NULL,
  `bi` varchar(16) DEFAULT NULL,
  `data_nascimento` date NOT NULL,
  `data_registo` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `genero` varchar(255) NOT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `nacionalidade` varchar(255) DEFAULT NULL,
  `nome` varchar(30) NOT NULL,
  `nome_imagen` varchar(255) DEFAULT NULL,
  `profissao` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `reset_password_token` varchar(45) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `sobre_mim` longtext,
  `sobrenome` varchar(30) NOT NULL,
  `telefone` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`codigo`, `bairro`, `bi`, `data_nascimento`, `data_registo`, `email`, `estado`, `genero`, `municipio`, `nacionalidade`, `nome`, `nome_imagen`, `profissao`, `provincia`, `reset_password_token`, `senha`, `sobre_mim`, `sobrenome`, `telefone`) VALUES
(1, 'Ferrovia', '000000001HL044', '1998-10-30', '2022-05-29', 'ppmulengap@gmail.com', b'1', 'MASCULINO', 'Lubango', 'Angolana', 'Pedro', '11LEO_3393.jpg', 'Engenheiro Informático', 'Huíla', NULL, '$2a$10$U136QR9ul/iq.KNhZcxSReOvLatI1WYMCREAj6q/OsC3qxK7/MtN6', 'Olá, sou um profissional dedicado com as atribuições que me são colocadas.\r\nObrigado por visitares o meu perfil', 'Mulenga', 936545785),
(2, 'Bairro Ferrovia', '000000001HL044', '1996-05-15', '2022-05-27', 'pedromulenga21@gmail.com', b'1', 'MASCULINO', 'Huambo', 'Angolana', 'Pedro', 'null1perfil.png', 'Gestor de Projectos', 'Huambo', NULL, '$2a$10$grRzP4AY3Wu8FAi6eQ3qk.PxZDVtE0xsP3T0XmUEMTHFbIXO99zpe', 'Brevemente', 'Paciência', 920454788),
(3, 'Ferrovia', '005917175HL055', '1998-06-14', '2022-05-27', 'eugenia@gmail.com', b'1', 'FEMENINO', 'Lubango', 'Angolana ', 'Eugénia Mariana', '320211223_222925.jpg', 'Enfermeira', 'Huíla', NULL, '$2a$10$FiqNvwgD8iyJdHH177Ami.t1txwTXe5NhQhmjjuinx2Z7tY9Uot4e', 'Olá eu sou a Enfermeira Eugénia Mariana Sagui, formada pelo Instituto superior Tundavala e este é o meu CV.', 'Sagui', 950507467);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario_grupo`
--

CREATE TABLE `usuario_grupo` (
  `codigo_usuario` bigint(20) NOT NULL,
  `codigo_grupo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `usuario_grupo`
--

INSERT INTO `usuario_grupo` (`codigo_usuario`, `codigo_grupo`) VALUES
(2, 2),
(3, 1),
(1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `certificacao`
--
ALTER TABLE `certificacao`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FKcgnmoohtg9bwr16upvunpyo33` (`usuario_codigo`);

--
-- Indexes for table `experiencia_profissional`
--
ALTER TABLE `experiencia_profissional`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FKjaoo6ikfln96m4javqgelyt0l` (`usuario_codigo`);

--
-- Indexes for table `formacao_academica`
--
ALTER TABLE `formacao_academica`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FKtc3stroiwka6pfy351m0itnd2` (`usuario_codigo`);

--
-- Indexes for table `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `grupo_permissao`
--
ALTER TABLE `grupo_permissao`
  ADD KEY `FKfp14wb9mt832y4jlw2rx3pf6p` (`codigo_permissao`),
  ADD KEY `FKh1lvrl72de4u5xhr1u3jvo0rq` (`codigo_grupo`);

--
-- Indexes for table `permissao`
--
ALTER TABLE `permissao`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `UK_5171l57faosmj8myawaucatdw` (`email`);

--
-- Indexes for table `usuario_grupo`
--
ALTER TABLE `usuario_grupo`
  ADD KEY `FK4yweq9u2sokki6o060mejfw8r` (`codigo_grupo`),
  ADD KEY `FKcx5f61jsftmpnlu4ec8fyndg3` (`codigo_usuario`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `certificacao`
--
ALTER TABLE `certificacao`
  MODIFY `codigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `experiencia_profissional`
--
ALTER TABLE `experiencia_profissional`
  MODIFY `codigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `formacao_academica`
--
ALTER TABLE `formacao_academica`
  MODIFY `codigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `grupo`
--
ALTER TABLE `grupo`
  MODIFY `codigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `permissao`
--
ALTER TABLE `permissao`
  MODIFY `codigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `certificacao`
--
ALTER TABLE `certificacao`
  ADD CONSTRAINT `FKcgnmoohtg9bwr16upvunpyo33` FOREIGN KEY (`usuario_codigo`) REFERENCES `usuario` (`codigo`);

--
-- Limitadores para a tabela `experiencia_profissional`
--
ALTER TABLE `experiencia_profissional`
  ADD CONSTRAINT `FKjaoo6ikfln96m4javqgelyt0l` FOREIGN KEY (`usuario_codigo`) REFERENCES `usuario` (`codigo`);

--
-- Limitadores para a tabela `formacao_academica`
--
ALTER TABLE `formacao_academica`
  ADD CONSTRAINT `FKtc3stroiwka6pfy351m0itnd2` FOREIGN KEY (`usuario_codigo`) REFERENCES `usuario` (`codigo`);

--
-- Limitadores para a tabela `grupo_permissao`
--
ALTER TABLE `grupo_permissao`
  ADD CONSTRAINT `FKfp14wb9mt832y4jlw2rx3pf6p` FOREIGN KEY (`codigo_permissao`) REFERENCES `permissao` (`codigo`),
  ADD CONSTRAINT `FKh1lvrl72de4u5xhr1u3jvo0rq` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`);

--
-- Limitadores para a tabela `usuario_grupo`
--
ALTER TABLE `usuario_grupo`
  ADD CONSTRAINT `FK4yweq9u2sokki6o060mejfw8r` FOREIGN KEY (`codigo_grupo`) REFERENCES `grupo` (`codigo`),
  ADD CONSTRAINT `FKcx5f61jsftmpnlu4ec8fyndg3` FOREIGN KEY (`codigo_usuario`) REFERENCES `usuario` (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
