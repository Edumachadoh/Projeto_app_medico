package com.up.clinica_digital.presentation.termsOfUse

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Viewmodel dos termos de uso do aplicativo
 * termos essses que serão acessados na tela
 * de cadastro do usuário [RegisterScreen]
 * */
@HiltViewModel
class TermsOfUseViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<TermsOfUseUiState>(
        TermsOfUseUiState.Success(
            title = "Termos de Uso", // O título da tela
            content = getTermsOfUseContent()
        )
    )
    val uiState: StateFlow<TermsOfUseUiState> = _uiState.asStateFlow()

    private fun getTermsOfUseContent(): String {

        return """
1. Introdução
Seja bem-vindo à nossa plataforma de comunidade médica. Estes Termos de Uso regem o acesso e a utilização dos serviços oferecidos pelo aplicativo, incluindo fóruns de discussão, networking entre profissionais e demais funcionalidades. Ao criar sua conta ou usar nossa plataforma, você concorda em cumprir integralmente estes termos.

2. Definições
Plataforma: conjunto de recursos e serviços acessíveis pelo aplicativo móvel.
Usuário: todo indivíduo que acessa ou utiliza a plataforma, englobando médicos e pacientes.
Dados Pessoais: quaisquer informações relacionadas a pessoa natural identificada ou identificável, conforme LGPD (Lei Geral de Proteção de Dados, Lei nº 13.709/2018).
LGPD: legislação brasileira que regula o tratamento de dados pessoais.

3. Aceitação dos Termos
Ao se cadastrar e utilizar nossos serviços, você declara ter capacidade civil e legal para celebrar este contrato e aceita que:
leu e compreendeu estes Termos de Uso na íntegra;
possui ciência do tratamento de seus dados conforme Política de Privacidade;
concorda com a coleta, uso e compartilhamento de dados para as finalidades aqui descritas.

4. Serviços e Funcionalidades
A plataforma oferece, entre outras:
cadastro e verificação de médicos (CRM validado via API externa);
agendamento de consultas e histórico compartilhável;
fóruns públicos para médicos;
rede de networking e encaminhamento entre profissionais.

5. Cadastro e Verificação de Usuários
Para se cadastrar como médico, é obrigatório informar CRM, RQE e demais documentos solicitados.
A verificação ocorre automaticamente por meio de consulta à API do Conselho Regional de Medicina.
Pacientes devem fornecer nome completo, CPF, telefone e e-mail válidos.
O não fornecimento de dados corretos pode resultar em suspensão ou exclusão da conta.

6. Coleta e Tratamento de Dados Pessoais (LGPD)
Coletamos somente os dados estritamente necessários ao fornecimento dos serviços descritos.
Os dados podem ser usados para:
validação de perfis profissionais;
análise estatística e melhoria contínua da plataforma.
Garantimos os direitos dos titulares de dados (acesso, retificação, eliminação, portabilidade e oposição), conforme artigos 18 a 21 da LGPD.
O Encarregado pelo Tratamento de Dados para assuntos relacionados à LGPD pode ser contatado pelo instagram @clinicadigitalcuritiba.

7. Segurança da Informação
Para proteger seus dados, adotamos:
monitoramento contínuo de vulnerabilidades e testes de penetração;
backups periódicos.
Você concorda em manter sigilo sobre credenciais de acesso e em notificar imediatamente qualquer uso indevido ou suspeita de intrusão.

8. Responsabilidades dos Usuários
Utilizar a plataforma conforme a legislação vigente e bons costumes;
Não compartilhar credenciais de terceiros nem acessar perfis alheios;
Respeitar a privacidade e dignidade de outros integrantes da comunidade;
Não publicar conteúdo ofensivo, ilícito ou não relacionado ao propósito médico;
Arcar pelas informações e materiais que enviar ou compartilhar na plataforma.

9. Conteúdo Gerado pelo Usuário
Todo material (textos, imagens, documentos) enviado ou publicado pelos usuários é de sua inteira responsabilidade. Concedem à plataforma licença não exclusiva, gratuita e livre de royalties para uso, reprodução e distribuição desse conteúdo, sempre respeitando sua autoria.

10. Propriedade Intelectual
O design, código-fonte, marcas e demais direitos relacionados à plataforma são propriedade exclusiva da empresa. É proibida qualquer reprodução ou engenharia reversa, salvo consentimento expresso por escrito.

11. Limitação de Responsabilidade
Na máxima extensão permitida por lei, a plataforma não se responsabiliza por:
danos indiretos, lucros cessantes ou perda de dados;
falhas de comunicação de internet ou sistemas de terceiros;
condutas de usuários, diagnósticos médicos ou tratamentos recomendados por profissionais;
impropriedade de uso que viole estes Termos ou legislações aplicáveis.

12. Alterações nos Termos
Reservamo-nos o direito de modificar estes Termos de Uso a qualquer momento. Notificaremos alterações significativas por e-mail ou aviso em tela no aplicativo. Caso você não concorde com as mudanças, deve interromper imediatamente o uso da plataforma.

13. Contato e Suporte
Para dúvidas, solicitações de direitos de titular ou demais assuntos relativos a estes Termos, entre em contato:
instagram: @clinicadigitalcuritiba

Última atualização: 29 de setembro de 2025.
        """.trimIndent()
    }
}