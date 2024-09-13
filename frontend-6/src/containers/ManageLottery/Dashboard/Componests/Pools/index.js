"use client";

import PropTypes from "prop-types";

import {
	Button,
	Col,
	Form,
	Input,
	message,
	Row,
	Space,
	Spin,
	Table,
} from "antd";
import { useForm } from "antd/lib/form/Form";
import { withRouter } from "next/router";
import { useEffect, useState } from "react";
import { PoolService } from "src/service/PoolService";
import dayjs from "dayjs";

const propTypes = {
	router: PropTypes.object.isRequired,
};

const defaultProps = {
	router: {},
};

const PoolDashboard = (props) => {
	const { router, userList = [] } = props;

	const [poolForm] = useForm();

	const poolService = new PoolService();

	const [pools, setPools] = useState([]);
	const [loading, setLoading] = useState(true);

	const poolSearch = () => {
		setLoading(true);
		poolService
			.getSearch()
			.then((data) => {
				setPools(data?.content);
			})
			.catch((error) => {
				const errorMessage =
					error.response?.data?.message || "Erro desconhecido";
				console.error(error);
				message.error(errorMessage);
			})
			.finally(() => {
				setLoading(false);
			});
	};

	useEffect(() => {
		poolSearch();
	}, []);

	const columns = [
		{
			title: "Criado em",
			dataIndex: "createdAt",
			key: "createdAt",
			render: (createdAt) => (
				<span>{dayjs(createdAt).format("DD/MM/YYYY HH:mm")}</span>
			),
		},
		{
			title: "Nome",
			dataIndex: "name",
			key: "name",
		},
		{
			title: "Cadastrado em",
			dataIndex: "status",
			key: "status",
		},
		{
			title: "Número máximo",
			dataIndex: "entryFee",
			key: "entryFee",
		},
		{
			title: "Apostas",
			dataIndex: "bets",
			key: "bets",
			render: (bets) => <div>{bets?.length}</div>,
		},
		{
			title: "Participantes",
			dataIndex: "participants",
			key: "participants",
			render: (participants) => <div>{participants?.length}</div>,
		},
	];

	return (
		<Spin spinning={loading} tip="carregando...">
			<Form
				name="PoolForm"
				form={poolForm}
				className="login-form"
				initialValues={{
					remember: true,
				}}
				onFinish={poolSearch}
				style={{
					width: "100%",
					padding: 20,
					margin: "0 auto 40px",
					borderRadius: 4,
					background: "#fff",
				}}
				size="large"
			>
				<Row>
					<Space>
						<Col>
							<Form.Item name="name">
								<Input
									maxLength={250}
									placeholder="Nome do bolão"
								/>
							</Form.Item>
						</Col>

						<Col>
							<Form.Item>
								<Space>
									<Button htmlType="submit">Pesquisar</Button>

									<Button
										onClick={() =>
											router.push(
												router.asPath +
													"/cadastrar-bolao"
											)
										}
									>
										Cadastrar novo bolão
									</Button>
								</Space>
							</Form.Item>
						</Col>
					</Space>
				</Row>
			</Form>
			<Table dataSource={pools} columns={columns} />;
		</Spin>
	);
};

PoolDashboard.propTypes = propTypes;

PoolDashboard.defaultProps = defaultProps;

export default withRouter(PoolDashboard);
